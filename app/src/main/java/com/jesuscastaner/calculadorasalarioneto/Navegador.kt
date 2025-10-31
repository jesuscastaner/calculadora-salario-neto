package com.jesuscastaner.calculadorasalarioneto

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

/**
 * Ruta de la vista del formulario.
 */
@Serializable
object RutaFormulario

/**
 * Ruta de la vista de resultados.
 */
@Serializable
data class RutaResultados(
    val salarioBruto: Double,
    val ss: Double,
    val irpf: Double,
    val salarioNeto: Double
)

/**
 * Navegador para alternar entre las diferentes vistas.
 */
@Composable
fun Navegador() {
    // crear un controlador para cambiar entre vistas
    val navController = rememberNavController()

    // definir el host y las funciones para cambiar entre vistas
    NavHost(
        navController = navController,
        startDestination = RutaFormulario
    ) {
        // vista del formulario
        composable<RutaFormulario> {
            VistaFormulario { resultados: Resultados ->
                navController.navigate(
                    route = RutaResultados(
                        salarioBruto = resultados.salarioBruto,
                        ss = resultados.ss,
                        irpf = resultados.irpf,
                        salarioNeto = resultados.salarioNeto
                    )
                )
            }
        }

        // vista de resultados
        composable<RutaResultados> { backStackEntry: NavBackStackEntry ->
            val resultados = backStackEntry.toRoute<RutaResultados>()

            VistaResultados(
                salarioBruto = resultados.salarioBruto,
                ss = resultados.ss,
                irpf = resultados.irpf,
                salarioNeto = resultados.salarioNeto,
            ) {
                navController.navigateUp()
            }
        }
    }
}
