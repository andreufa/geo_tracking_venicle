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

class MotionBroadcastReceiver(private val callback:(Int)-> Unit) : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Registered activity", Toast.LENGTH_SHORT).show()
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)!!
            for (event in result.transitionEvents) {
                if(event.transitionType == DetectedActivity.IN_VEHICLE) {
                    callback.invoke(event.activityType)
                }
            }
        }
    }
}
