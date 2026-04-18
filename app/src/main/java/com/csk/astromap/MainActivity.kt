package com.csk.astromap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.csk.astromap.ui.MySolutionApp
import com.csk.astromap.ui.theme.AstroMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AstroMap {
                MySolutionApp()
            }
        }
    }

}