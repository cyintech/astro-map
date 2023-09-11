package com.csk.myjpmcandroid.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.csk.myjpmcandroid.R
import com.csk.myjpmcandroid.ui.screen.HomeScreen
import com.csk.myjpmcandroid.ui.viewmodel.ISSInfoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySolutionApp() {
    val issInfoViewModel: ISSInfoViewModel = hiltViewModel()
    Scaffold(topBar = { MyTopAppBar() }) { innerPadding->
        HomeScreen(issInfoViewModel, modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.toolbar_title),
            style = MaterialTheme.typography.headlineMedium
        )
    })
}
