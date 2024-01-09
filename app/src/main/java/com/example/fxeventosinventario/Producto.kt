package com.example.fxeventosinventario

import java.io.Serializable

data class Producto(
    var NombreProducto: String? = null,
    var descripcion: String? = null,
    var lote: String? = null,
    var fechaVencimiento: String? = null,
    var ubicacion : String? = null,
    var stock: String? = null
) : Serializable
