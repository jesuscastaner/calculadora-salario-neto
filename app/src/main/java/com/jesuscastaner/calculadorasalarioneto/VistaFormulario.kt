package com.jesuscastaner.calculadorasalarioneto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Vista del formulario donde el usuario introduce los datos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaFormulario(mostrarResultados: (Resultados) -> Unit) {
    // valores que debe introducir el usuario
    // (la funcion 'remember' permite conservar el estado de las variables entre recomposiciones)
    var salarioBruto by remember { mutableStateOf(value = "0.0") }
    var numPagas by remember { mutableIntStateOf(value = 14) }
    var edad by remember { mutableStateOf(value = "16") }
    var discapacidad by remember { mutableStateOf(value = "0") }
    var numHijos by remember { mutableStateOf(value = "0") }

    val gruposProf = listOf(
        "Grupo 1: Personal ingeniero, licenciado y de alta dirección",
        "Grupo 2: Personal ingeniero técnico, peritos y ayudantes con titulación",
        "Grupo 3: Jefatura administrativa y de taller",
        "Grupo 4: Ayudantes sin titulación",
        "Grupo 5: Personal oficial administrativo",
        "Grupo 6: Personal subalterno",
        "Grupo 7: Personal auxiliar administrativo",
        "Grupo 8: Oficiales de primera y segunda",
        "Grupo 9: Oficiales de tercera y especialistas",
        "Grupo 10: Peones",
        "Grupo 11: Menores de 18 años"
    )
    var expandidoGrupoProf by remember { mutableStateOf(value = false) }
    var grupoProf by remember { mutableStateOf(value = gruposProf.first()) }

    val estadosCiviles = listOf(
        "Soltero",
        "Casado",
        "Separado",
        "Divorciado",
        "Viudo"
    )
    var expandidoEstadoCivil by remember { mutableStateOf(value = false) }
    var estadoCivil by remember { mutableStateOf(value = estadosCiviles.first()) }

    // el salario bruto no puede ser negativo:
    val salarioBrutoInvalido = salarioBruto.isNotEmpty() &&
        salarioBruto.toDoubleOrNull()?.takeIf { it >= 0 } == null

    // en Espana, la edad minima legal para trabajar es 16 anos:
    val edadInvalida = edad.isNotEmpty() &&
        edad.toIntOrNull()?.takeIf { it >= 16 } == null

    // el grado de discapacidad solo puede estar entre 0% y 100%
    val discapacidadInvalida = discapacidad.isNotEmpty() &&
        discapacidad.toIntOrNull()?.let { it < 0 || it > 100 } ?: true

    // el numero de hijos no puede ser negativo
    val numHijosInvalido = numHijos.isNotEmpty() &&
        numHijos.toIntOrNull()?.takeIf { it >= 0 } == null

    // valor para controlar que todos los valores introducidos en el formulario sean validos
    // (el boton 'Calcular' de abajo no se activa mientras alguno de ellos sea invalido)
    val formularioValido =
        !(salarioBrutoInvalido || edadInvalida || discapacidadInvalida || numHijosInvalido)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 60.dp,
                vertical = 30.dp
            )
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 20.dp)
    ) {
        // cabecera
        Cabecera(
            titulo = "Formulario",
            descripcion = "Introduce tus datos y pulsa 'Calcular' para obtener los resultados."
        )

        // formulario
        Column(verticalArrangement = Arrangement.spacedBy(space = 10.dp)) {
            // campo para el salario bruto anual
            TextField(
                value = salarioBruto,
                onValueChange = { it ->
                    if (it.matches(Regex("\\d*\\.?\\d*"))) {
                        salarioBruto = it
                    }
                },
                label = { Text(text = "Salario bruto anual") },
                suffix = { Text(text = "€/año") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = salarioBrutoInvalido,
                modifier = Modifier.fillMaxWidth()
            )

            if (salarioBrutoInvalido) {
                MensajeError("El salario bruto no puede ser negativo.")
            }

            // radio buttons para el numero de pagas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = numPagas == 12,
                        onClick = { numPagas = 12 }
                    )
                    Text(text = "12 pagas")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = numPagas == 14,
                        onClick = { numPagas = 14 }
                    )
                    Text(text = "14 pagas")
                }
            }

            // campo para la edad
            TextField(
                value = edad,
                onValueChange = { it ->
                    if (it.isEmpty() || it.matches(Regex("\\d+"))) {
                        edad = it
                    }
                },
                label = { Text(text = "Edad") },
                suffix = { Text(text = "años") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = edadInvalida,
                modifier = Modifier.fillMaxWidth()
            )

            if (edadInvalida) {
                MensajeError("Debes tener 16 o más años.")
            }

            // campo para el grupo profesional
            ExposedDropdownMenuBox(
                expanded = expandidoGrupoProf,
                onExpandedChange = { expandidoGrupoProf = !expandidoGrupoProf }
            ) {
                TextField(
                    value = grupoProf.substringBefore(delimiter = ":"),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Grupo profesional") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoGrupoProf)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        )
                )

                ExposedDropdownMenu(
                    expanded = expandidoGrupoProf,
                    onDismissRequest = { expandidoGrupoProf = false }
                ) {
                    gruposProf.forEachIndexed { i, it ->
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                grupoProf = it
                                expandidoGrupoProf = false
                            }
                        )
                    }
                }
            }

            // campo para el grado de discapacidad
            TextField(
                value = discapacidad,
                onValueChange = { it ->
                    if (it.isEmpty() || it.matches(Regex("\\d+"))) {
                        discapacidad = it
                    }
                },
                label = { Text(text = "Grado de discapacidad") },
                suffix = { Text(text = "%") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = discapacidadInvalida,
                modifier = Modifier.fillMaxWidth()
            )

            if (discapacidadInvalida) {
                MensajeError("Introduce un valor entre 0% y 100%.")
            }

            // campo para el estado civil
            ExposedDropdownMenuBox(
                expanded = expandidoEstadoCivil,
                onExpandedChange = { expandidoEstadoCivil = !expandidoEstadoCivil }
            ) {
                TextField(
                    value = estadoCivil,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Estado civil") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEstadoCivil)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        )
                )

                ExposedDropdownMenu(
                    expanded = expandidoEstadoCivil,
                    onDismissRequest = { expandidoEstadoCivil = false }
                ) {
                    estadosCiviles.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                estadoCivil = it
                                expandidoEstadoCivil = false
                            }
                        )
                    }
                }
            }

            // campo para el numero de hijos
            TextField(
                value = numHijos,
                onValueChange = { it ->
                    if (it.isEmpty() || it.matches(Regex("\\d+"))) {
                        numHijos = it
                    }
                },
                label = { Text(text = "Número de hijos") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = numHijosInvalido,
                modifier = Modifier.fillMaxWidth()
            )

            if (numHijosInvalido) {
                MensajeError("El número de hijos no puede ser negativo.")
            }
        }

        // boton para enviar los valores a la calculadora y mostrar los resultados
        Button(
            onClick = {
                // calcular resultados
                val resultados = calcularResultados(
                    salarioBruto = salarioBruto.toDoubleOrNull() ?: 0.0,
                    numPagas = numPagas,
                    edad = edad.toIntOrNull() ?: 0,
                    grupoProf = gruposProf.indexOf(grupoProf) + 1,
                    discapacidad = discapacidad.toIntOrNull() ?: 0,
                    estadoCivil = estadoCivil,
                    numHijos = numHijos.toIntOrNull() ?: 0
                )

                // ir a la vista de resultados
                mostrarResultados(resultados)
            },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calcular")
        }
    }
}
