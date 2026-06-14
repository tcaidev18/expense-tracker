package com.aitc.expensetrackerandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.aitc.expensetrackerandroid.data.local.entity.AppMetadataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppMetadataDao {
    @Query("SELECT * FROM app_metadata WHERE id = 0 LIMIT 1")
    fun observeMetadata(): Flow<AppMetadataEntity?>

    @Query("SELECT COUNT(*) FROM app_metadata")
    suspend fun count(): Int
}
