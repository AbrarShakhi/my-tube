package com.github.abrarshakhi.mytube.presentation.home.state

sealed interface SheetState{
    object Visible: SheetState
    object Hidden: SheetState
}
