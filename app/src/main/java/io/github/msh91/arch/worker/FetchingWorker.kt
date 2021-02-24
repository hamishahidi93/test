package io.github.msh91.arch.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.stealthcopter.networktools.PortScan
import io.github.msh91.arch.data.model.inquiryServer.InquiryServerDto
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import io.github.msh91.arch.data.model.inspectedServer.InspectedServerResponseDto
import io.github.msh91.arch.data.model.isPName.IsPNameResponseDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class FetchingWorker(
    application: Context,
    workerParameters: WorkerParameters,
    private val inspectorsRepository: InspectorsRepository
) : RxWorker(application, workerParameters) {
    override fun createWork(): Single<Result> {
        getIsPName()
        Log.d(TAG , "start")
        return Single.just(Result.success())

    }

    var isPName: String = ""

    private fun sendInspectedServer(inquiryServerDto: InquiryServerDto) {
        val params: HashMap<String, String> = HashMap()
        params["server"] = inquiryServerDto.id
        params["hash_key"] = inquiryServerDto.hash_key
        params["ip"] = inquiryServerDto.ip
        params["received_isp"] = isPName
        params["is_active"] = inquiryServerDto.connection_status
        inspectorsRepository.sendInspectedServer(params)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<InspectedServerResponseDto>() {
                override fun onNext(responseDto: InspectedServerResponseDto) {
                    Log.d(TAG , "received_isp: "+ responseDto.received_isp +" ip: "+ responseDto.ip)

                    insertServerToDb(responseDto.id, responseDto.hash_key, responseDto.ip, responseDto.received_isp,  inquiryServerDto.connection_status)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })


    }


    private fun getIsPName() {
        inspectorsRepository.getIsPName()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<IsPNameResponseDto>() {
                override fun onNext(responseDto: IsPNameResponseDto) {
                    isPName = responseDto.isp
                    Log.d(TAG , "isPName: "+isPName)

                    getInquiryServers()
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })

    }

    private fun getInquiryServers() {
        inspectorsRepository.getInquiryServer()
            .flatMapIterable { servers -> servers }
            .flatMap { server -> scanIpPorts(server) }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<List<InquiryServerDto>>() {
                override fun onNext(tradeItems: List<InquiryServerDto>) {
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })

    }


    private fun scanIpPorts(inquiryServerDto: InquiryServerDto): Observable<InquiryServerDto> {

        val portListener = object : PortScan.PortListener {
            override fun onResult(portNo: Int, open: Boolean) {

            }

            override fun onFinished(openPorts: java.util.ArrayList<Int>?) {
                if (openPorts?.size!! > 0) {
                    inquiryServerDto.connection_status = "true"
                } else {
                    inquiryServerDto.connection_status = "false"

                }
                Log.d(TAG , "connection_status: "+ inquiryServerDto.connection_status +" ip: "+inquiryServerDto.ip)

                sendInspectedServer(inquiryServerDto)
            }

        }
        PortScan.onAddress(inquiryServerDto.ip).setTimeOutMillis(1000).setPorts(inquiryServerDto.ports.map { it.toInt() } as ArrayList<Int>).setMethodTCP().doScan(portListener);

        return Observable.just(inquiryServerDto)

    }


    private fun insertServerToDb(serverId: String, hashKey: String, ip: String,receivedIsp: String, isActive: String) {

        Completable.fromRunnable {
            inspectorsRepository.insertServerToDb(serverId, hashKey, ip,receivedIsp,  isActive)
        }.subscribeOn(Schedulers.io())
            .subscribe {
                Log.d(TAG , "insertServerToDb: ")

            }


    }

    companion object{
        val TAG  = "FETCHING_WORKER_TAG"
    }
    class Factory @Inject constructor(
        private val foo: Provider<InspectorsRepository>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return FetchingWorker(
                appContext,
                params,
                foo.get()
            )
        }
    }


}
