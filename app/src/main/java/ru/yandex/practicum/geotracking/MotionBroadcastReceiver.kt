package ru.yandex.practicum.geotracking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity

class MotionBroadcastReceiver(private val callback: (Int) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)!!
            for (event in result.transitionEvents) {
                if (event.activityType == DetectedActivity.IN_VEHICLE) {
                    callback.invoke(event.transitionType)
                }
            }
        }
    }
}
