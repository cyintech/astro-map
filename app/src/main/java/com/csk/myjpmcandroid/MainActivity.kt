package com.csk.myjpmcandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.csk.myjpmcandroid.ui.MySolutionApp
import com.csk.myjpmcandroid.ui.theme.MyJPMCAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyJPMCAndroidTheme {
                MySolutionApp()
            }
        }
    }

}