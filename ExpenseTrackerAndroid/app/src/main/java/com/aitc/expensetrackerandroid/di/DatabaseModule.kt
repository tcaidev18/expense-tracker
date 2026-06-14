package com.aitc.expensetrackerandroid.di

import android.content.Context
import androidx.room.Room
import com.aitc.expensetrackerandroid.data.local.ExpenseTrackerDatabase
import com.aitc.expensetrackerandroid.data.local.dao.AppMetadataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ExpenseTrackerDatabase = Room.databaseBuilder(
        context,
        ExpenseTrackerDatabase::class.java,
        "expense_tracker.db",
    ).build()

    @Provides
    fun provideAppMetadataDao(database: ExpenseTrackerDatabase): AppMetadataDao =
        database.appMetadataDao()
}
