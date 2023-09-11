package com.csk.myjpmcandroid

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.csk.myjpmcandroid.ui.screen.MySolutionApp
import com.csk.myjpmcandroid.ui.theme.MyJPMCAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyJPMCAndroidTheme {
                MySolutionApp()
            }
        }
    }

}
