package io.github.msh91.arch.app

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.msh91.arch.BuildConfig
import io.github.msh91.arch.di.component.DaggerAppComponent
import android.os.Build
import androidx.work.* import io.github.msh91.arch.di.component.AppComponent
import io.github.msh91.arch.worker.FetchingWorker
import io.github.msh91.arch.worker.SampleWorkerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import java.util.stream.DoubleStream.builder
import javax.inject.Inject

/**
 * Custom [Application] class for app that prepare app for running
 */
class ArchApplication : DaggerApplication() ,Configuration.Provider {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject lateinit var daggerAwareWorkerFactory: SampleWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initDebugModeValues()
        val config = Configuration.Builder()
            .setWorkerFactory(daggerAwareWorkerFactory)
            .build()
        WorkManager.initialize(this, config)

        applicationScope.launch {
            setupRecurringWork()
        }
        DaggerAppComponent.factory().create(this).inject(this);

    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setRequiresDeviceIdle(true)
                }
            }
//            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequest
            .Builder(FetchingWorker::class.java,15, TimeUnit.MINUTES)
//            .setInitialDelay(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "FetchingWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)

    private fun initDebugModeValues() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()


}
