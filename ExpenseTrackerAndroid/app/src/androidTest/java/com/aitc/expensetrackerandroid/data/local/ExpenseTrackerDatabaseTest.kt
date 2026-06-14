package com.aitc.expensetrackerandroid.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aitc.expensetrackerandroid.data.local.dao.AppMetadataDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpenseTrackerDatabaseTest {

    private lateinit var database: ExpenseTrackerDatabase
    private lateinit var dao: AppMetadataDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ExpenseTrackerDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = database.appMetadataDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun openDatabase_metadataTableIsEmpty() = runBlockingTest {
        assertEquals(0, dao.count())
    }

    private fun runBlockingTest(block: suspend () -> Unit) {
        kotlinx.coroutines.runBlocking { block() }
    }
}
