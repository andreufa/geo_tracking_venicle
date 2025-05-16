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
//        Toast.makeText(context, "${intent.action}", Toast.LENGTH_SHORT).show()
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)!!
            Toast.makeText(context, "Result ${result.transitionEvents}", Toast.LENGTH_LONG).show()
            for (event in result.transitionEvents) {
                if (event.activityType == DetectedActivity.WALKING) {
                    callback.invoke(event.transitionType)
                }
            }
        }
    }
}
