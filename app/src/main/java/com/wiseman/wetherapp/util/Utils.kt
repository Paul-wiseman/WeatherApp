package com.wiseman.wetherapp.util

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

fun Context.checkPermission(permissionString: String): Boolean =
    ActivityCompat.checkSelfPermission(this, permissionString) == PackageManager.PERMISSION_GRANTED

fun Context.makeToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}