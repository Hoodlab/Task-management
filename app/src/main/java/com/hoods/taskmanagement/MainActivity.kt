package com.hoods.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hoods.taskmanagement.presentation.TaskApp
import com.hoods.taskmanagement.presentation.ui.theme.TaskManagementTheme
import com.hoods.taskmanagement.utils.Utils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.createNotificationChannel(this)
        enableEdgeToEdge()
        setContent {
            TaskManagementTheme {
                TaskApp()
            }
        }
    }
}
