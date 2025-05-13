package ru.yandex.practicum.geotracking

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.yandex.practicum.geotracking.ui.theme.GeotrackingTheme

class MainActivity : ComponentActivity() {
    private val taskState = MutableStateFlow<TaskState>(TaskState.TaskInit)
    private val transitions = listOf(
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.IN_VEHICLE)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.IN_VEHICLE)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build()
    )

    private val request = ActivityTransitionRequest(transitions)

    private val motionPendingIntent: PendingIntent by lazy {
        val intent = Intent(this, MotionBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    override fun onResume() {
        super.onResume()
        val task = ActivityRecognition.getClient(this)
            .requestActivityTransitionUpdates(request, motionPendingIntent)
        task.addOnSuccessListener {
            taskState.update { TaskState.TaskCompleted("Начало отслеживания движения: Успешно") }
        }

        task.addOnFailureListener { e: Exception ->
            taskState.update { TaskState.TaskFail("Начало отслеживания движения: Ошибка") }
        }
    }

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    override fun onPause() {
        super.onPause()
        val task = ActivityRecognition.getClient(this)
            .removeActivityTransitionUpdates(motionPendingIntent)

        task.addOnSuccessListener {
            motionPendingIntent.cancel()
            taskState.update { TaskState.TaskCompleted("Остановка отслеживания движения: Успешно") }
        }

        task.addOnFailureListener { e: Exception ->
            taskState.update { TaskState.TaskFail("Остановка отслеживания движения: Ошибка") }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeotrackingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlayerScreen(
                        motionState = motionState,
                        taskState = taskState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    companion object {
        val motionState = MutableStateFlow(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
    }
}

