package io.github.msh91.arch.ui.home.list

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.stealthcopter.networktools.PortScan
import io.github.msh91.arch.data.model.Error
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import io.github.msh91.arch.data.model.inquiryServer.InquiryServerDto
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.ui.base.BaseViewModel
import io.github.msh91.arch.ui.register.RegisterViewModel
import io.github.msh91.arch.util.livedata.NonNullLiveData
import io.github.msh91.arch.util.livedata.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeListViewModel @Inject constructor(
    private val inspectorsRepository: InspectorsRepository
) : BaseViewModel() {
    val allServers: LiveData<List<ServerModel>>
        get() = inspectorsRepository.getServerModels().flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)


    var error: SingleEventLiveData<Error>? = null
    var isLoading: SingleEventLiveData<Boolean>? = null

    var serverDetail: LiveData<ServerModel>? = null

    var isPName: String = ""

    init {
        error = SingleEventLiveData()
        isLoading = SingleEventLiveData()
        serverDetail = SingleEventLiveData()

        val handler = Handler()
        handler.postDelayed({
            isLoading!!.value = true
            getIsPName()
            getInquiryServer()

        }, 1000)
    }

    private fun getIsPName() {

        viewModelScope.launch {
            when (val either = inspectorsRepository.getIsPName()) {
                is Either.Right -> {
                    isPName = either.b.isp

                }
                is Either.Left -> {
                    error?.value = either.a
                }

            }

        }
    }

    private fun sendInspectedServer(serverModel: ServerModel) {
        val params: HashMap<String, String> = HashMap()
        params["server"] = serverModel.ServerId
        params["hash_key"] = serverModel.HashKey
        params["ip"] = serverModel.Ip
        params["received_isp"] = isPName
        params["is_active"] = if(serverModel.IsActive == "active"){ "true" } else{"false"}

        viewModelScope.launch {
            when (val either = inspectorsRepository.sendInspectedServer(params)) {
                is Either.Right -> {

                }
                is Either.Left -> {
                    error?.value = either.a
                }
            }
        }
    }

    private fun getInquiryServer() {

        viewModelScope.launch {
            when (val either = inspectorsRepository.getInquiryServer()) {
                is Right -> {
                    isLoading!!.value = false

                    (either.b).forEach { server ->
                        launch {
                            scanIpPorts(server.ip, server)

                        }
                    }
                    Log.d(TAG, (either.b).toString() + " inquiryServers")


                }
                is Left -> {
                    error?.value = either.a
                }

            }

        }
    }


    private fun scanIpPorts(ip: String, inquiryServerDto: InquiryServerDto) {

        insertServer(inquiryServerDto.id, inquiryServerDto.hash_key, inquiryServerDto.ip, inquiryServerDto.ports, "pending")

        val portListener = object : PortScan.PortListener {
            override fun onResult(portNo: Int, open: Boolean) {

            }

            override fun onFinished(openPorts: java.util.ArrayList<Int>?) {
                if (openPorts?.size!! > 0) {
                    updateServer("active", inquiryServerDto)
                } else {
                    updateServer("not active", inquiryServerDto)

                }

            }

        }
        PortScan.onAddress(ip).setTimeOutMillis(1000).setPorts(inquiryServerDto.ports.map { it.toInt() } as ArrayList<Int>).setMethodTCP().doScan(portListener);

    }


    private fun insertServer(serverId: String, hashKey: String, ip: String, ports: List<String>
                             , isActive: String) {
        viewModelScope.launch {
            inspectorsRepository.insertServer(serverId, hashKey, ip, ports, isActive)
        }
    }

    fun updateServer(isActive: String, inquiryServerDto: InquiryServerDto) {
        viewModelScope.launch {
            inspectorsRepository.updateServer(isActive, inquiryServerDto.id)

//            sendInspectedServer(inspectorsRepository.getServerDetail(inquiryServerDto.id))
        }
    }


    private fun deleteAllServers() {
        viewModelScope.launch {
            inspectorsRepository.deleteAll()
        }
    }

    private fun getServerDetail(serverId: String) {
        viewModelScope.launch {
            inspectorsRepository.getServerDetail(serverId)
        }
    }

    companion object {
        private const val TAG = "HomeListViewModel"
    }
}
