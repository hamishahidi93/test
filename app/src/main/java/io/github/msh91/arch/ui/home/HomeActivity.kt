package io.github.msh91.arch.ui.home

import android.os.Build
import android.os.Bundle
import androidx.work.*
import io.github.msh91.arch.R
import io.github.msh91.arch.databinding.ActivityHomeBinding
import io.github.msh91.arch.ui.base.BaseActivity
import io.github.msh91.arch.worker.FetchingWorker
import io.github.msh91.arch.worker.SampleWorkerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(),Configuration.Provider{

    override val layoutId: Int = R.layout.activity_home
    override val viewModel: HomeViewModel by getLazyViewModel()

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var daggerAwareWorkerFactory: SampleWorkerFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = Configuration.Builder()
            .setWorkerFactory(daggerAwareWorkerFactory)
            .build()
        WorkManager.initialize(this, config)

        applicationScope.launch {
            setupRecurringWork()
        }

    }

    override fun getWorkManagerConfiguration() =

        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

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

}
