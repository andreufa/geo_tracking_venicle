package ru.yandex.practicum.geotracking

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionEvent
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.Geofence

class MotionBroadcastReceiver(private val callback: (Int) -> Unit) : BroadcastReceiver() {

    init {
        Log.i(TAG, "MotionBroadcastReceiver created")
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Registered activity in MotionBroadcastReceiver")
        callback.invoke(999)
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)!!
            for (event in result.transitionEvents) {
                if (event.transitionType == DetectedActivity.IN_VEHICLE) {
                    callback.invoke(event.activityType)
                }
            }
        }
    }
}
