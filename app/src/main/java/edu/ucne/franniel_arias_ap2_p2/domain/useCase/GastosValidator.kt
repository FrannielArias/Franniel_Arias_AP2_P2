package edu.ucne.franniel_arias_ap2_p2.domain.useCase

data class GastosValidator(
    val isValid: Boolean,
    val error: String? = null
)

fun validateSuplidor(value: String) : GastosValidator{
    if(value.isBlank())
        return GastosValidator(false,"El suplidor no puede estar vacia.")

    if(value.length < 3)
        return GastosValidator(false , "El suplidor debe tener mas de 2 letras")

    return GastosValidator(true)
}