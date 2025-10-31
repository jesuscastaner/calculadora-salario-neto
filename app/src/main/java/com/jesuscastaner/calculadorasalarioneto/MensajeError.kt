package com.jesuscastaner.calculadorasalarioneto

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

/**
 * Muestra un mensaje de error en color rojo.
 */
@Composable
fun MensajeError(mensaje: String) {
    Text(
        text = mensaje,
        fontSize = 12.sp,
        color = Color.Red
    )
}
