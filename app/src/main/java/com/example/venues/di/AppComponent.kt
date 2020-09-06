package com.example.venues.di

import android.content.Context
import com.example.venues.VenueApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    MainActivityBuilder::class])

interface AppComponent : AndroidInjector<VenueApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}