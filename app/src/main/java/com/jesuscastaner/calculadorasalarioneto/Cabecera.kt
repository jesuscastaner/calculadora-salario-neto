package com.jesuscastaner.calculadorasalarioneto

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Cabecera reutilizable para las vistas, con titulo y descripcion.
 */
@Composable
fun Cabecera(titulo: String, descripcion: String) {
    // titulo
    Text(
        text = titulo,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )

    // descripcion
    Text(text = descripcion)
}
