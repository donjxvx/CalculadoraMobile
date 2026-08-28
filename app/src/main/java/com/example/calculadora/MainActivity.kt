package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var currentOperand by remember { mutableStateOf("") }
    var previousOperand by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var history by remember { mutableStateOf("") }

    fun formatResult(result: Double): String {
        return if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()
    }

    fun onNumberClick(number: String) {
        if (number == "." && currentOperand.contains(".")) return
        currentOperand += number
    }

    fun onOperatorClick(op: String) {
        if (currentOperand.isEmpty() && previousOperand.isEmpty()) return

        if (previousOperand.isNotEmpty() && currentOperand.isNotEmpty()) {
            val num1 = previousOperand.toDoubleOrNull() ?: 0.0
            val num2 = currentOperand.toDoubleOrNull() ?: 0.0
            val res = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "×" -> num1 * num2
                "÷" -> if (num2 != 0.0) num1 / num2 else 0.0
                else -> 0.0
            }
            previousOperand = formatResult(res)
        } else if (currentOperand.isNotEmpty()) {
            previousOperand = currentOperand
        }

        operator = op
        currentOperand = ""
        history = "$previousOperand $operator"
    }

    fun onEqualsClick() {
        if (currentOperand.isEmpty() || previousOperand.isEmpty()) return

        val num1 = previousOperand.toDoubleOrNull() ?: 0.0
        val num2 = currentOperand.toDoubleOrNull() ?: 0.0

        val result = when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "×" -> num1 * num2
            "÷" -> if (num2 != 0.0) num1 / num2 else 0.0
            else -> 0.0
        }

        history = "$previousOperand $operator $currentOperand ="
        currentOperand = formatResult(result)
        previousOperand = ""
        operator = ""
    }

    fun onClearClick() {
        currentOperand = ""
        previousOperand = ""
        operator = ""
        history = ""
    }

    fun onSignClick() {
        if (currentOperand.isEmpty()) return
        val number = currentOperand.toDoubleOrNull() ?: return
        currentOperand = formatResult(number * -1)
    }

    fun onPercentClick() {
        if (currentOperand.isEmpty()) return
        val number = currentOperand.toDoubleOrNull() ?: return
        currentOperand = formatResult(number / 100)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF22252D))
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = history,
                color = Color(0xFF8B8B93),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = if (currentOperand.isEmpty()) "0" else currentOperand,
                color = Color.White,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold
            )
        }

        val spacing = 12.dp

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing)) {
            CalculatorButton("C", Color(0xFFA5A5A5), Color.Black, Modifier.weight(1f)) { onClearClick() }
            CalculatorButton("+/-", Color(0xFFA5A5A5), Color.Black, Modifier.weight(1f)) { onSignClick() }
            CalculatorButton("%", Color(0xFFA5A5A5), Color.Black, Modifier.weight(1f)) { onPercentClick() }
            CalculatorButton("÷", Color(0xFFFF6B6B), Color.White, Modifier.weight(1f)) { onOperatorClick("÷") }
        }
        Spacer(modifier = Modifier.height(spacing))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing)) {
            CalculatorButton("7", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("7") }
            CalculatorButton("8", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("8") }
            CalculatorButton("9", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("9") }
            CalculatorButton("×", Color(0xFFFF6B6B), Color.White, Modifier.weight(1f)) { onOperatorClick("×") }
        }
        Spacer(modifier = Modifier.height(spacing))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing)) {
            CalculatorButton("4", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("4") }
            CalculatorButton("5", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("5") }
            CalculatorButton("6", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("6") }
            CalculatorButton("-", Color(0xFFFF6B6B), Color.White, Modifier.weight(1f)) { onOperatorClick("-") }
        }
        Spacer(modifier = Modifier.height(spacing))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing)) {
            CalculatorButton("1", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("1") }
            CalculatorButton("2", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("2") }
            CalculatorButton("3", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick("3") }
            CalculatorButton("+", Color(0xFFFF6B6B), Color.White, Modifier.weight(1f)) { onOperatorClick("+") }
        }
        Spacer(modifier = Modifier.height(spacing))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing)) {
            CalculatorButton("0", Color(0xFF292D36), Color.White, Modifier.weight(2f)) { onNumberClick("0") }
            CalculatorButton(".", Color(0xFF292D36), Color.White, Modifier.weight(1f)) { onNumberClick(".") }
            CalculatorButton("=", Color(0xFF4ECDC4), Color.Black, Modifier.weight(1f)) { onEqualsClick() }
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(if (symbol == "0") 2.1f else 1f) // O botão '0' é mais largo
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Text(
            text = symbol,
            color = textColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )
    }
}