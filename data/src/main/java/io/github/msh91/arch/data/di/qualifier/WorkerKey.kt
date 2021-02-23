package io.github.msh91.arch.data.di.qualifier

import androidx.work.ListenableWorker
import androidx.work.RxWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
