package com.dr.drillinstructor.ui.events

sealed class MainEvent

// events for the main activity
object OpenSettings : MainEvent()
object StartTraining : MainEvent()
object StopTraining : MainEvent()