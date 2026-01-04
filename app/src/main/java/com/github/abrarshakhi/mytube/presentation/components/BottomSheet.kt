package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.presentation.home.HomeViewModel
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(viewModel: HomeViewModel, sheetState: SheetState) {
    ModalBottomSheet(
        onDismissRequest = { viewModel.defaultState() },
        sheetState = sheetState
    ) {
        val addChannelState by viewModel.addChannelState.collectAsState()
        val addChannelWithFilterState by viewModel.addChannelWithFilterState.collectAsState()

        val handleTextState by viewModel.handleTextState.collectAsState()
        val filterTextState by viewModel.filterTextState.collectAsState()
        val filterShouldContainState by viewModel.filterShouldContainState.collectAsState()
        val infoState by viewModel.infoState.collectAsState()

        Column {
            AddChannelContent(
                handle = handleTextState,
                onTextFieldValueChange = { viewModel.setHandleText(it) },
                onButtonClick = { viewModel.findChannel(handleTextState.trim()) },
                addChannelState = addChannelState
            )
            Spacer(modifier = Modifier.height(24.dp))
            when (val state = addChannelState) {
                is AddChannelState.Found ->
                    AddFilterContent(
                        filter = filterTextState,
                        switchChecked = filterShouldContainState,
                        onTextFieldValueChange = { viewModel.setFilterText(it) },
                        onSwitchCheckedChange = { viewModel.setFilterShouldContain(it) },
                        onButtonClick = {
                            viewModel.addChannel(
                                state.channel,
                                regex = filterTextState.trim(),
                                switchChecked = filterShouldContainState
                            )
                        },
                        infoText = infoState,
                        addChannelWithFilterState = addChannelWithFilterState
                    )

                else -> Unit
            }
        }
    }
}