package com.abhat.anumathi.internal

import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

internal object RationaleManager : Rationale {
    override fun shouldShowPermissionRequestRationale(
        activity: ComponentActivity,
        permission: String
    ): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            permission
        )
    }
}

internal interface Rationale {
    fun shouldShowPermissionRequestRationale(
        activity: ComponentActivity,
        permission: String
    ): Boolean
}