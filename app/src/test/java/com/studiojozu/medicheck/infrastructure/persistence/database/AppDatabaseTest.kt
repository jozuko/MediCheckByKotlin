package com.studiojozu.medicheck.infrastructure.persistence.database

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class AppDatabaseTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun test() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        assertNotNull(database)
    }
}
