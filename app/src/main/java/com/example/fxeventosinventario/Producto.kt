package com.example.fxeventosinventario

import java.util.Date

data class Producto(
    val NombreProducto: String? = null,
    val descripcion: String? = null,
    val lote: String? = null,
    val fechaVencimiento: String? = null,
    val ubicacion : String? = null,
    val stock: String? = null
)
