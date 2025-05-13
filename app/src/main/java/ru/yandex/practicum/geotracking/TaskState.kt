package ru.yandex.practicum.geotracking

sealed class TaskState(val message:String) {
    data object TaskInit:TaskState("")
    data class TaskCompleted(val success:String):TaskState(success)
    data class TaskFail(val error:String):TaskState(error)
}