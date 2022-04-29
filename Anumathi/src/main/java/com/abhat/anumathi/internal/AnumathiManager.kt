package com.abhat.anumathi.internal

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.abhat.anumathi.PermissionResult
import kotlinx.coroutines.flow.MutableSharedFlow

internal object AnumathiManager {

    val anumathiResults = MutableSharedFlow<Map<String, PermissionResult>>(
        extraBufferCapacity = 1
    )

    fun processAnumathi(
        activity: ComponentActivity,
        permissions: Map<String, Boolean>,
        rationale: Rationale
    ): Map<String, PermissionResult> {
        val result = permissions.map { (permission, isGranted) ->
            val state = if (isGranted) {
                PermissionResult.Granted
            } else {
                val shouldShowRationale = rationale.shouldShowPermissionRequestRationale(
                    activity,
                    permission
                )
                PermissionResult.Denied(shouldShowRationale)
            }
            permission to state
        }.toMap()
        return result
    }

    fun requestPermissionsFor(
        activity: ComponentActivity
    ): ActivityResultLauncher<Array<String>> {
        return activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val result = processAnumathi(activity, permissions, RationaleManager)
            anumathiResults.tryEmit(result)
        }
    }

    fun requestPermissionsFor(
        fragment: Fragment
    ): ActivityResultLauncher<Array<String>> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val result = processAnumathi(fragment.requireActivity(), permissions, RationaleManager)
            anumathiResults.tryEmit(result)
        }
    }

}