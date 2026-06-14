package com.aitc.expensetrackerandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aitc.expensetrackerandroid.data.local.converter.DateConverters
import com.aitc.expensetrackerandroid.data.local.dao.AppMetadataDao
import com.aitc.expensetrackerandroid.data.local.entity.AppMetadataEntity

@Database(
    entities = [AppMetadataEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(DateConverters::class)
abstract class ExpenseTrackerDatabase : RoomDatabase() {
    abstract fun appMetadataDao(): AppMetadataDao
}
