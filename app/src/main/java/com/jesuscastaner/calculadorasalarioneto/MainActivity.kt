package com.jesuscastaner.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jesuscastaner.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme

/**
 * Punto de entrada a la app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraSalarioNetoTheme {
                App()
            }
        }
    }
}

