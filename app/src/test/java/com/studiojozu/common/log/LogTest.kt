package com.studiojozu.common.log

import com.studiojozu.medicheck.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import android.util.Log as AndroidLog

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class LogTest {

    @Test
    @Throws(Exception::class)
    fun d_message() {
        val log = Log(LogTest::class.java)
        log.d("debug message")

        assertLogged(AndroidLog.DEBUG, "MediCheck.LogTest", "debug message", null)
    }

    @Test
    @Throws(Exception::class)
    fun i_message() {
        val log = Log(LogTest::class.java)
        log.i("info message")

        assertLogged(AndroidLog.INFO, "MediCheck.LogTest", "info message", null)
    }

    @Test
    @Throws(Exception::class)
    fun i_throwableMessage() {
        val log = Log(LogTest::class.java)
        val throwable = Exception("exception message")
        log.i(throwable)

        assertLogged(AndroidLog.INFO, "MediCheck.LogTest", "exception message", throwable)
    }

    @Test
    @Throws(Exception::class)
    fun i_throwableNoMessage() {
        val log = Log(LogTest::class.java)
        val throwable = Exception()
        log.i(throwable)

        assertLogged(AndroidLog.INFO, "MediCheck.LogTest", throwable.toString(), throwable)
    }

    @Test
    @Throws(Exception::class)
    fun w_message() {
        val log = Log(LogTest::class.java)
        log.w("warn message")

        assertLogged(AndroidLog.WARN, "MediCheck.LogTest", "warn message", null)
    }

    @Test
    @Throws(Exception::class)
    fun e_throwableMessage() {
        val log = Log(LogTest::class.java)
        val throwable = Exception("exception message")
        log.e(throwable)

        assertLogged(AndroidLog.ERROR, "MediCheck.LogTest", "exception message", throwable)
    }

    @Test
    @Throws(Exception::class)
    fun e_throwableNoMessage() {
        val log = Log(LogTest::class.java)
        val throwable = Exception()
        log.e(throwable)

        assertLogged(AndroidLog.ERROR, "MediCheck.LogTest", throwable.toString(), throwable)
    }

    private fun assertLogged(type: Int, tag: String, msg: String, throwable: Throwable?) {
        val lastLog = ShadowLog.getLogs()[0]
        assertEquals(type.toLong(), lastLog.type.toLong())
        assertEquals(msg, lastLog.msg)
        assertEquals(tag, lastLog.tag)
        assertEquals(throwable, lastLog.throwable)
    }
}
