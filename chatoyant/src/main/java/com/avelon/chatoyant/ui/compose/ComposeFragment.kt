package com.avelon.chatoyant.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.databinding.FragmentComposeBinding

class ComposeFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(ComposeFragment::class.java)
    }

    private var _binding: FragmentComposeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreateView()")
        _binding = FragmentComposeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val composeView = binding.composeView

        composeView.apply {
            // setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // app()
                dumbButton("hello") {
                    throw Exception()
                }
            }
        }

        return root
    }

    @Composable
    fun dumbButton(
        text: String,
        onClick: () -> Unit,
    ) {
        Button(onClick = onClick) {
            Text(text = text)
        }
    }

    @Preview
    @Composable
    fun app() {
        DLog.d(TAG, "App()")

        Row {
            // Image(painterResource(android.R.drawable.btn_radio))
            AnimatedVisibility(true) {
                Text(
                    text = "Mungo",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Box {
        }
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        16.dp,
                    ).background(LightGray)
                    .border(2.dp, Color.Blue, shape = RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(16.dp).background(DarkGray))
            Text(text = "000", fontSize = TextUnit(96f, TextUnitType.Sp))
            Text(text = "km/h", fontSize = TextUnit(48f, TextUnitType.Sp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Cola")
            Button(onClick = { DLog.d(TAG, "onClick()") }) {
                DLog.d(TAG, "onContent()")
                Text(text = "clickMe")
            }

            OutlinedTextField(
                value = "Hanna",
                onValueChange = { DLog.d(TAG, "onValueChange()") },
                label = { DLog.d(TAG, "onLabel()") },
            )
        }
        /*Header()
        EditButton()
        Body()
        Item()*/
    }

    fun test() {}

    override fun onDestroyView() {
        DLog.d(TAG, "onDestroyView()")
        super.onDestroyView()
        _binding = null
    }
}
