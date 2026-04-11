package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun calculate(num1: Double, operator: String, num2: Double): String {
    return when (operator) {
        "+" -> "Hasil: ${num1 + num2}"
        "-" -> "Hasil: ${num1 - num2}"
        "*" -> "Hasil: ${num1 * num2}"
        "/" -> {
            if (num2 != 0.0) "Hasil: ${num1 / num2}"
            else "Error: Tidak bisa dibagi dengan nol!"
        }
        else -> "Operator tidak valid!"
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var num1Input by remember { mutableStateOf("") }
    var num2Input by remember { mutableStateOf("") }
    var selectedOperator by remember { mutableStateOf("+") }
    var result by remember { mutableStateOf("") }

    val operators = listOf("+", "-", "*", "/")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "KALKULATOR SEDERHANA",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // input-1
        OutlinedTextField(
            value = num1Input,
            onValueChange = { num1Input = it },
            label = { Text("Angka Pertama") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // operator
        Text(
            text = "Pilih Operator:",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            operators.forEach { op ->
                FilterChip(
                    selected = selectedOperator == op,
                    onClick = { selectedOperator = op },
                    label = {
                        Text(
                            text = op,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }

        // input-2
        OutlinedTextField(
            value = num2Input,
            onValueChange = { num2Input = it },
            label = { Text("Angka Kedua") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )

        // kalkulasi
        Button(
            onClick = {
                val n1 = num1Input.toDoubleOrNull()
                val n2 = num2Input.toDoubleOrNull()
                result = when {
                    n1 == null || n2 == null -> "Error: Masukkan angka yang valid!"
                    else -> calculate(n1, selectedOperator, n2)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = "Hitung", fontSize = 18.sp)
        }

        // hasil
        if (result.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = result,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}