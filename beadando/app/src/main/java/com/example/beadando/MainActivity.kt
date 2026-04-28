package com.example.beadando

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.beadando.ui.theme.BeadandoTheme
import kotlin.concurrent.thread
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(MyLifeCircleObserver())
        enableEdgeToEdge()
        setContent {
            BeadandoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDo(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ToDo(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    var outputText by remember {mutableStateOf("")}

    val activity = LocalActivity.current as? Activity

    Column(
        modifier= modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = outputText,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
        )

        OutlinedTextField(value=inputText,
            onValueChange = {inputText = it},
            label = {Text("Write something...")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d("Button", "Button clicked")
            outputText = inputText

                thread {
                    try {
                        Thread.sleep(5000)
                        activity?.runOnUiThread {
                            outputText = "Feldolgozás befejezve."
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                    }
                }
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("Küldés")
        }

    }
}

class MyLifeCircleObserver : DefaultLifecycleObserver {
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        Log.d("LifeCycleLog", "Activity is stopped.")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("LifeCycleLog", "Activity started.")

    }

}
