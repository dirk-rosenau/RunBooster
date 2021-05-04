package com.dr.drillinstructor.ui.events

// TODO brauch ich so ein event?
sealed class TrainingEvent
object StopClicked : TrainingEvent()
object PauseClicked : TrainingEvent()
object SkipClicked : TrainingEvent()