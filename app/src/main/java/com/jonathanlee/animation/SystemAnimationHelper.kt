package com.jonathanlee.animation

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log

/**
 * A helper object to get/set system animation
 *
 * Note that this requires a system level permission, so must grant permission manually through ADB
 *
 * ]adb shell pm grant com.jonathanlee.animation android.permission.WRITE_SECURE_SETTINGS
 */
object SystemAnimationHelper {

    private const val TAG = "setAnimation"
    private const val WRITE_SECURE_PERMISSION = "android.permission.WRITE_SECURE_SETTINGS"
    private var isSetAnimationOn: Boolean? = null

    fun getAnimation(contentResolver: ContentResolver): Boolean {
        val animator = Settings.Global.getFloat(
            contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1F
        )
        val transition = Settings.Global.getFloat(
            contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 1F
        )
        val window = Settings.Global.getFloat(
            contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 1F
        )
        return (animator != 0F && transition != 0F && window != 0F)
    }

    fun setAnimation(contentResolver: ContentResolver, status: Boolean) {
        try {
            if (status) {
                Settings.Global.putFloat(
                    contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1F
                )
                Settings.Global.putFloat(
                    contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 1F
                )
                Settings.Global.putFloat(
                    contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 1F
                )
                isSetAnimationOn = true
            } else {
                Settings.Global.putFloat(
                    contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 0F
                )
                Settings.Global.putFloat(
                    contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 0F
                )
                Settings.Global.putFloat(
                    contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 0F
                )
                isSetAnimationOn = false
            }
        } catch (e: SecurityException) {
            Log.d(TAG, e.toString())
            isSetAnimationOn = false
        }
    }

    fun isSetAnimationOn(): Boolean? {
        return isSetAnimationOn
    }

    fun isPermissionGiven(context: Context): Boolean {
        val permissionStatus = context.checkCallingOrSelfPermission(WRITE_SECURE_PERMISSION)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }
}