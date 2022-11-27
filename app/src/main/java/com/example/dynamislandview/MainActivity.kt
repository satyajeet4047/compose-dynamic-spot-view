package com.example.dynamislandview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.constraintlayout.compose.ExperimentalMotionApi
import com.example.dynamislandview.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMotionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                DynamicView()
            }
        }
    }
}

