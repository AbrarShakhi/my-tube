package com.github.abrarshakhi.mytube.presentation.home.state

sealed interface HomeState{
    object Sheet: HomeState
    object Dialog: HomeState
    object Hidden: HomeState
}
