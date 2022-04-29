package com.abhat.anumathi

sealed class PermissionResult {
    object Granted : PermissionResult()
    data class Denied(val shouldShowRationale: Boolean) : PermissionResult()
}