package com.dr.drillinstructor.ui.events

sealed class AmbientEvent
object EnterAmbient : AmbientEvent()
object ExitAmbient : AmbientEvent()
object UpdateAmbient : AmbientEvent()
