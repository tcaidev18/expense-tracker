package com.aitc.expensetrackerandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Internal metadata entity so Room can initialize before business entities are added.
 */
@Entity(tableName = "app_metadata")
data class AppMetadataEntity(
    @PrimaryKey val id: Int = 0,
    val schemaVersion: Int = 1,
)
