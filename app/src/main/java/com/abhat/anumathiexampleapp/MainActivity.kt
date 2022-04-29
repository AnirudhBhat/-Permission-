package com.abhat.anumathiexampleapp

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.abhat.anumathi.PermissionResult
import com.abhat.anumathi.anumathi
import com.abhat.anumathi.registerPermissions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val registerPermission = registerPermissions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val result = anumathi(
                registerPermission,
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                )
            )
            result.map { (permission, state) ->
                when (state) {
                    is PermissionResult.Denied -> {
                        Log.d("ANUMATHI", "Permission Denied for ${permission}: Rationale - " + state.shouldShowRationale)
                    }
                    PermissionResult.Granted -> {
                        Log.d("ANUMATHI", "Permission Granted ${permission}")
                    }
                }
            }
        }
    }
}