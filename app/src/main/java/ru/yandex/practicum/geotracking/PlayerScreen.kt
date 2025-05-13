package ru.yandex.practicum.geotracking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.DetectedActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlayerScreen(
    motionState: StateFlow<Int>,
    taskState: StateFlow<TaskState>,
    modifier: Modifier = Modifier
){
    val typeMotion by motionState.collectAsState()
    val taskMessage by taskState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.dark_side_of_moon),
            contentDescription = "Обложка альбома"
        )

    }

}

@Preview
@Composable
fun PreviewPlayerScreen(){
    PlayerScreen(
        motionState = MutableStateFlow(ActivityTransition.ACTIVITY_TRANSITION_ENTER),
        taskState = MutableStateFlow(TaskState.TaskInit)
    )
}