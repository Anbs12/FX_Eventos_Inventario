package com.example.fxeventosinventario

import java.util.Date

data class Producto(
    var NombreProducto: String? = null,
    var descripcion: String? = null,
    var lote: String? = null,
    var fechaVencimiento: String? = null,
    var ubicacion : String? = null,
    var stock: String? = null
)
