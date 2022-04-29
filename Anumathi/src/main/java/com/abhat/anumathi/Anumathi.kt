package com.abhat.anumathi

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.abhat.anumathi.internal.AnumathiManager
import kotlinx.coroutines.flow.first


suspend fun ComponentActivity.anumathi(
    permissionsLauncher: ActivityResultLauncher<Array<String>>,
    permissions: Array<String>
): Map<String, PermissionResult> {
    return anumathi(this, permissionsLauncher, permissions)
}

fun ComponentActivity.registerPermissions(): ActivityResultLauncher<Array<String>> {
    return AnumathiManager.requestPermissionsFor(this)
}

suspend fun Fragment.anumathi(
    permissionsLauncher: ActivityResultLauncher<Array<String>>,
    permissions: Array<String>
): Map<String, PermissionResult> {
    return anumathi(requireContext(), permissionsLauncher, permissions)
}

fun Fragment.registerPermissions(): ActivityResultLauncher<Array<String>> {
    return AnumathiManager.requestPermissionsFor(this)
}

private suspend fun anumathi( context: Context,
    permissionsLauncher: ActivityResultLauncher<Array<String>>,
                     permissions: Array<String>): Map<String, PermissionResult> {
    val allPermissionsGranted = permissions.all { isPermissionGrantedFor(context, it) }

    return if (allPermissionsGranted) {
        permissions.map {
            it to PermissionResult.Granted
        }.toMap()
    } else {
        permissionsLauncher.launch(permissions)
        AnumathiManager.anumathiResults.first()
    }
}

fun isPermissionGrantedFor(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}
