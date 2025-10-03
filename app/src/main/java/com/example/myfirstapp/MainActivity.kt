package com.example.myfirstapp

import android.R.attr.onClick
import android.R.attr.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.rememberComposableLambdaN
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.AppTheme
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(true) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var resultadoTexto by remember { mutableStateOf("") }
    var errorTexto by remember { mutableStateOf("") }
    var totalConPropina by remember { mutableDoubleStateOf(0.0) }
    var porcentajePropina by remember { mutableIntStateOf(0) }
    Column (modifier = modifier.padding(16.dp)){
        TextField(
            value =  text1,
            onValueChange = { newText1 -> text1 = newText1 },
            label = { Text(stringResource(R.string.label_amount)) },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value =  text2,
            onValueChange = { newText2 -> text2 = newText2 },
            label = { Text(stringResource(R.string.label_people)) },
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            Text(
                text = stringResource(R.string.label_round_tip),
                modifier = modifier
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }

        Text(
            text = stringResource(R.string.label_rating),
            modifier = modifier
        )
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 4,
            valueRange = 0f..25f,
            enabled = checked
        )
        if (!checked){
            sliderPosition = 0f
        }

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current

        Button(onClick = {
            errorTexto = ""
            resultadoTexto = ""
            val cantidad = text1.toDoubleOrNull()
            val comensales = text2.toIntOrNull()
            if (cantidad == null || cantidad <= 0) {
                errorTexto = context.getString(R.string.error_invalid_amount)
                return@Button
            }
            if (comensales == null || comensales <= 0) {
                errorTexto = context.getString(R.string.error_invalid_people)
                return@Button
            }
            if (checked) {
                porcentajePropina = 5 * sliderPosition.toInt()
                totalConPropina = cantidad + (cantidad * porcentajePropina) / 100
            } else {
                totalConPropina = cantidad
            }
            val costePorPersona = totalConPropina / comensales

            resultadoTexto = context.getString(R.string.result_total, totalConPropina) + "\n" +
                    context.getString(R.string.result_each, costePorPersona)
        }) {
            Text(stringResource(R.string.btn_calculate))
        }

        if (errorTexto.isNotEmpty()) {
            Text(text = errorTexto)
        }

        if (resultadoTexto.isNotEmpty()) {
            Text(text = resultadoTexto)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}