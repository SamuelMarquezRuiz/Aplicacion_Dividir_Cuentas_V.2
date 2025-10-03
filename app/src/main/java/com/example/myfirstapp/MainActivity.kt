package com.example.myfirstapp

import android.R.attr.onClick
import android.R.attr.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAppTheme {
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
            onValueChange = {newText1 -> text1 = newText1},
            label = {Text("Cantidad")},
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value =  text2,
            onValueChange = {newText2 -> text2 = newText2},
            label = {Text("Comensales")},
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            Text(
                text = "Redondear Propina",
                modifier = modifier
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
        }

        Text(
            text = "Valoración",
            modifier = modifier
        )

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..5f,
            steps = 4,
            enabled = checked
        )

        Button(onClick = {
            errorTexto = ""
            resultadoTexto = ""
            val cantidad = text1.toDoubleOrNull()
            val comensales = text2.toIntOrNull()
            if (cantidad == null || cantidad <= 0) {
                errorTexto = "Indique una cantidad válida"
                return@Button
            }

            if (comensales == null || comensales <= 0) {
                errorTexto = "Indique uno o más comensales"
                return@Button
            }

            if (checked) {
                porcentajePropina = 5 * sliderPosition.toInt()
                totalConPropina = cantidad + (cantidad * porcentajePropina) / 100
            }else{
                totalConPropina = cantidad
            }

            val costePorPersona = totalConPropina / comensales

            resultadoTexto = "Cantidad total: ${"%.1f".format(totalConPropina)} €\n" +
                    "Cada uno: ${"%.1f".format(costePorPersona)} €"
        }) {
            Text("Calcular")
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
    MyFirstAppTheme {
        Greeting("Android")
    }
}