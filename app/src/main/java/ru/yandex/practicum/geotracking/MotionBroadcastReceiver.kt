package ru.yandex.practicum.geotracking

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity

class MotionBroadcastReceiver(private val callback: (Int) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Registered activity in MotionBroadcastReceiver")
        val res = ActivityTransitionResult.extractResult(intent)
        Toast.makeText(context, "${intent.action}", Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "Result $res", Toast.LENGTH_LONG).show()
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)!!
            Toast.makeText(context, "Result $result", Toast.LENGTH_SHORT).show()
            for (event in result.transitionEvents) {
                if (event.transitionType == DetectedActivity.IN_VEHICLE) {
                    callback.invoke(event.activityType)
                }
            }
        }
    }
}
