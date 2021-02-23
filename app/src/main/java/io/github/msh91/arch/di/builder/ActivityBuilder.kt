package io.github.msh91.arch.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.msh91.arch.ui.home.HomeActivity
import io.github.msh91.arch.ui.home.HomeFragmentProvider
import io.github.msh91.arch.ui.register.RegisterActivity
import io.github.msh91.arch.ui.splash.SplashActivity

/**
 * The Main Module for binding all of activities.
 * Every Activity should contribute with it's related modules
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(HomeFragmentProvider::class)])
    internal abstract fun bindHomeActivity(): HomeActivity
    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun bindRegisterActivity(): RegisterActivity
}
