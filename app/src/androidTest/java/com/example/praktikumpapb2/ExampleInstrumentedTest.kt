package com.example.praktikumpapb2

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<android.content.Context>()
        assertEquals("com.example.praktikumpapb2", appContext.packageName)
    }
}
