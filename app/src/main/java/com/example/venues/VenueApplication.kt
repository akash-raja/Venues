package com.example.venues

import android.app.Application
import com.example.venues.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class VenueApplication : Application(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(applicationContext).inject(this)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}