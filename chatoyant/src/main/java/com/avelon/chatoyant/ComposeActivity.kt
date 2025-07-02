package com.avelon.chatoyant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.ui.theme.ChatoyantTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatoyantTheme {
                Text(text = "David")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatoyantTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            for (i in 0..10) {
                Column {
                    MyCheckbox("Hello world")
                    MySwitch("Test")
                    Divider()
                    Row {
                        Text(text = "Habba")
                        BasicTextField(state = rememberTextFieldState("SSS"))
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
fun MyCheckbox(text: String) {
    Row {
        Text(text = text)
        Checkbox(checked = false, onCheckedChange = { o ->
            DLog.d("TAG", "aaaa=$o")
        })
    }
}

@Composable
fun MySwitch(text: String) {
    Row {
        Text(text = text)
        Switch(checked = false, onCheckedChange = null)
    }
}
