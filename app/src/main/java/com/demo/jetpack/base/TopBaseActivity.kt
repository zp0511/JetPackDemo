package com.demo.jetpack.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by zp on 2019/8/30.
 */
abstract class TopBaseActivity : AppCompatActivity() {
    private var mPermissionListener: PermissionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityStackManager.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityStackManager.removeActivity(this)
    }

    /** 权限申请 */
    fun onRuntimePermissionsAsk(permissions: Array<String>, listener: PermissionListener) {
        this.mPermissionListener = listener
        val activity = ActivityStackManager.getTopActivity()
        val deniedPermissions: MutableList<String> = mutableListOf()

        permissions
            .filterNot { ContextCompat.checkSelfPermission(activity!!, it) == PackageManager.PERMISSION_GRANTED }
            .forEach { deniedPermissions.add(it) }

        if (deniedPermissions.isEmpty())
            mPermissionListener!!.onGranted()
        else
            ActivityCompat.requestPermissions(activity!!, deniedPermissions.toTypedArray(), 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            val deniedPermissions: MutableList<String> = mutableListOf()
            if (grantResults.isNotEmpty()) {
                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        deniedPermissions.add(permissions[i])
                }

                if (deniedPermissions.isEmpty())
                    mPermissionListener!!.onGranted()
                else
                    mPermissionListener!!.onDenied(deniedPermissions)
            }
        }
    }
}