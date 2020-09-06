package com.example.venues.di

import android.content.Context
import com.example.venues.ui.search.database.VenueDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): VenueDatabase {
        return VenueDatabase.getDatabase(context)
    }
}