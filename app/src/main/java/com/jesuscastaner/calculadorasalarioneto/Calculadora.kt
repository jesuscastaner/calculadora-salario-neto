package com.jesuscastaner.calculadorasalarioneto

/**
 * Tipo de dato para almacenar los resultados del calculo.
 */
data class Resultados(
    val salarioBruto: Double,
    val ss: Double,
    val irpf: Double,
    val salarioNeto: Double
)

/**
 * A partir de los valores introducidos, devuelve el salario bruto, la cotizacion a la
 * Seguridad Social, la retencion de IRPF y el salario neto.
 */
fun calcularResultados(
    salarioBruto: Double,
    numPagas: Int,
    edad: Int,
    grupoProf: Int,
    discapacidad: Int,
    estadoCivil: String,
    numHijos: Int
): Resultados {
    // calcular cotizacion a la Seguridad Social
    val ss = calcularSS(
        salarioBruto = salarioBruto,
        numPagas = numPagas,
        grupoProf = grupoProf
    )

    // calcular retencion de IRPF
    val irpf = calcularIrpf(
        salarioBruto = salarioBruto,
        edad = edad,
        discapacidad = discapacidad,
        estadoCivil = estadoCivil,
        numHijos = numHijos
    )

    // calcular salario neto
    val salarioNeto = salarioBruto - ss - irpf

    // devolver resultados
    return Resultados(
        salarioBruto = salarioBruto,
        ss = ss,
        irpf = irpf,
        salarioNeto = salarioNeto
    )
}

/**
 * Devuelve la cantidad total que el trabajador cotiza anualmente a la Seguridad Social.
 */
private fun calcularSS(
    salarioBruto: Double,
    numPagas: Int,
    grupoProf: Int
): Double {
    // establecer las bases minima y maxima de cotizacion para su grupo profesional.
    // cada grupo tiene una minima distinta, pero la maxima es la misma para todos los grupos
    // fuente: https://www.grupo2000.es/que-seguros-sociales-debo-aplicar-en-2020-bases-y-tipos-de-cotizacion/
    val baseMin = when (grupoProf) {
        1 -> 1929.0
        2 -> 1599.6
        3 -> 1391.7
        in 4..11 -> 1381.2
        else -> error("Grupo profesional inválido")
    }
    val baseMax = 4909.5

    // calcular la base de cotizacion a partir del salario bruto mensual,
    // salvo que este sea inferior a la base minima o superior a la base maxima,
    // en cuyo caso se utilizaran esos valores
    var baseCotiz = maxOf(
        a = salarioBruto / numPagas,
        b = baseMin
    )
    baseCotiz = minOf(
        a = baseCotiz,
        b = baseMax
    )

    // calcular el tipo de cotizacion
    // fuente: https://www.grupo2000.es/que-seguros-sociales-debo-aplicar-en-2020-bases-y-tipos-de-cotizacion/
    val comunes = 0.047
    val desempleo = 0.0155
    val formacion = 0.001
    val mei = comunes * 0.0013
    val tipoCotiz = comunes + desempleo + formacion + mei

    // calcular y devolver la cotizacion total anual
    // (para evitar valores negativos inesperados, si el resultado es menor que el salario bruto,
    // se utiliza el salario bruto, que es como si cotizase el 100% de su sueldo)
    return minOf(
        a = baseCotiz * tipoCotiz * numPagas,
        b = salarioBruto
    )
}

/**
 * Devuelve la cantidad total de IRPF que se retiene anualmente al trabajador.
 */
private fun calcularIrpf(
    salarioBruto: Double,
    edad: Int,
    discapacidad: Int,
    estadoCivil: String,
    numHijos: Int
): Double {
    // comprobar si hay que retener IRPF al trabajador
    if (hayQueRetenerIRPF(
            salarioBruto = salarioBruto,
            discapacidad = discapacidad,
            estadoCivil = estadoCivil,
            numHijos = numHijos
        )
    ) {
        // en caso de que si, calcular y devolver la retencion que le corresponde
        // segun los tramos de IRPF
        // fuente: https://www.bankinter.com/blog/finanzas-personales/como-calcular-retenciones-irpf-nomina
        var irpf = when {
            salarioBruto <= 0 -> 0.0
            salarioBruto <= 12450 -> 0.19
            salarioBruto <= 20199 -> 0.24
            salarioBruto <= 35199 -> 0.30
            salarioBruto <= 59999 -> 0.37
            salarioBruto <= 299999 -> 0.45
            else -> 0.47
        }

        // aplicar reduccion segun la edad (para pensiones de jubilacion)
        // fuente: https://www.heraldo.es/noticias/nacional/2021/03/20/ventajas-fiscales-mayores-65-anos-renta-declaracion-1479255.html
        irpf *= when {
            edad in 60..65 -> 0.24
            edad in 66..69 -> 0.2
            edad >= 70 -> 0.08
            else -> 1.0
        }

        // calcular y devolver irpf total
        return salarioBruto * irpf
    } else {
        // en caso de que no, devolver 0€ (sin retencion)
        return 0.0
    }
}

/**
 * Determina si cabe retener IRPF o no al trabajador.
 */
private fun hayQueRetenerIRPF(
    salarioBruto: Double,
    discapacidad: Int,
    estadoCivil: String,
    numHijos: Int,
): Boolean {
    // establecer la base del minimo exento segun el estado civil
    var minimoExento = when (estadoCivil.lowercase()) {
        "soltero", "separado", "divorciado" -> 17000
        "viudo" -> 15000
        "casado" -> 11000
        else -> 0
    }

    // incrementar el minimo exento en funcion del numero de hijos, hasta un maximo de 3 hijos
    minimoExento += 1000 * minOf(
        a = numHijos,
        b = 3
    )

    // incrementar el minimo exento en funcion del grado de discapacidad:
    // + 3000€ si la discapacidad es >= 33% y < 54%
    // + 9000€ si la discapacidad es >= 65%
    // fuente: https://www.fundacionuniversia.net/actualidad/renta-2025-discapacidad-deducciones-que-te-pueden-beneficiar.html
    minimoExento += when {
        discapacidad in 33..54 -> 3000
        discapacidad >= 65 -> 9000
        else -> 0
    }

    // determinar si se debe aplicar retencion o no
    return salarioBruto > minimoExento
}
