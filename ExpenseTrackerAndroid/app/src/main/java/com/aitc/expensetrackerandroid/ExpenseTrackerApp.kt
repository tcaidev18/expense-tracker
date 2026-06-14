package com.aitc.expensetrackerandroid

import android.app.Application
import com.aitc.expensetrackerandroid.repository.UserSettingsRepository
import com.aitc.expensetrackerandroid.util.LocaleApplier
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class ExpenseTrackerApp : Application() {

    @Inject
    lateinit var userSettingsRepository: UserSettingsRepository

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            LocaleApplier.apply(userSettingsRepository.getLocale())
        }
    }
}
