package com.abhat.anumathi

import com.abhat.anumathi.internal.AnumathiManager
import com.abhat.anumathi.internal.Rationale
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AnumathiTest {
    private val rationaleManager: Rationale = mock()

    @Test
    fun `given permissions list, when process anumathi called, then return valid map`() {
        runBlocking {
            whenever(
                rationaleManager.shouldShowPermissionRequestRationale(any(), any())
            ).thenReturn(false)
            val permissionsMap = mapOf(
                "CALL_PHONE" to false,
                "LOCATION" to true
            )
            val expectedMap = mapOf(
                "CALL_PHONE" to PermissionResult.Denied(false),
                "LOCATION" to PermissionResult.Granted
            )

            val actualMap = AnumathiManager.processAnumathi(mock(), permissionsMap, rationaleManager)

            Assert.assertEquals(expectedMap, actualMap)
        }
    }

    @Test
    fun `given permissions list, when process anumathi called, then return valid map with rationale`() {
        runBlocking {
            whenever(
                rationaleManager.shouldShowPermissionRequestRationale(any(), any())
            ).thenReturn(true)
            val permissionsMap = mapOf(
                "CALL_PHONE" to false,
                "LOCATION" to false
            )
            val expectedMap = mapOf(
                "CALL_PHONE" to PermissionResult.Denied(true),
                "LOCATION" to PermissionResult.Denied(true)
            )

            val actualMap = AnumathiManager.processAnumathi(mock(), permissionsMap, rationaleManager)

            Assert.assertEquals(expectedMap, actualMap)
        }
    }
}