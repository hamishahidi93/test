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
class ArchApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initDebugModeValues()
        DaggerAppComponent.factory().create(this).inject(this);
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)

    private fun initDebugModeValues() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }


}
