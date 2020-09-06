package com.example.venues.di

import com.example.venues.ui.MainActivity
import com.example.venues.ui.details.VenueDetailsFragment
import com.example.venues.ui.search.VenueSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilder {

    @ContributesAndroidInjector()
    internal abstract fun contributeMainActivity(): MainActivity


    @ContributesAndroidInjector()
    internal abstract fun contributeSearchFragment(): VenueSearchFragment

    @ContributesAndroidInjector()
    internal abstract fun contributeDetailsFragment(): VenueDetailsFragment

}
