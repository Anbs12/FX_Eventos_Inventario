package com.example.fxeventosinventario

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ActualizarProductosActivity : AppCompatActivity() {

    //instanciar base de datos Firebase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var productoArrayList: ArrayList<Producto>

    //variables de los EditText
    var txtView_nombreActualizarProducto: TextView? = null
    var txt_actualizarNomrbeProducto: EditText? = null
    var txt_actualizarDescripcion: EditText? = null
    var txt_actualizarNumero_deLote: EditText? = null
    var txt_actualizarUbicacion_producto: EditText? = null
    var txt_stock_actualizarProductos: EditText? = null

    //variable del boton para actualizar
    var button_actualizar_producto: Button? = null
    var button_actualizarFecha_vencimiento: ImageButton? = null

    //variables datepicker
    var txtDate_actualizarFechaVencimiento: EditText? = null
    var datePicker_actualizarFechaVencimiento: DatePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_productos)

        databaseReference = FirebaseDatabase.getInstance().reference
        //Llamando a las variables
        txtView_nombreActualizarProducto = findViewById(R.id.txtView_nombreActualizarProducto)
        txt_actualizarNomrbeProducto = findViewById(R.id.txt_actualizarNomrbeProducto)
        txt_actualizarDescripcion = findViewById(R.id.txt_actualizarDescripcion)
        txt_actualizarNumero_deLote = findViewById(R.id.txt_actualizarNumero_deLote)
        txt_actualizarUbicacion_producto = findViewById(R.id.txt_actualizarUbicacion_producto)
        txt_stock_actualizarProductos = findViewById(R.id.txt_stock_actualizarProductos)
        txtDate_actualizarFechaVencimiento = findViewById(R.id.txtDate_actualizarFechaVencimiento)

        button_actualizar_producto = findViewById(R.id.button_actualizar_producto)
        button_actualizarFecha_vencimiento = findViewById(R.id.button_actualizarFecha_vencimiento)
        datePicker_actualizarFechaVencimiento =
            findViewById(R.id.datePicker_actualizarFechaVencimiento)

        txtDate_actualizarFechaVencimiento?.setText(getFechaDatePicker())
        posicionamientoTxtView()

        //inicializamos el array
        productoArrayList = arrayListOf<Producto>()

        button_actualizarFecha_vencimiento?.setOnClickListener {
            muestraCalendario(View(this))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_actualizarFechaVencimiento?.setOnDateChangedListener { datePicker_fechaVencimiento, anio, mes, dia ->
                txtDate_actualizarFechaVencimiento?.setText(getFechaDatePicker())
                datePicker_fechaVencimiento?.visibility = View.GONE
            }
        }

        button_actualizar_producto?.setOnClickListener() {
            actualizarProducto()
        }



    }

    private fun actualizarProducto() {
        // Obtener datos desde los EditText
        val nombreproducto = txt_actualizarNomrbeProducto?.text.toString()
        val descripcion = txt_actualizarDescripcion?.text.toString()
        val numLote = txt_actualizarNumero_deLote?.text.toString()
        val fecha = txtDate_actualizarFechaVencimiento?.text.toString()
        val ubicacion = txt_actualizarUbicacion_producto?.text.toString()
        val stock = txt_stock_actualizarProductos?.text.toString()

        // Obtener el producto desde el Intent
        val producto = intent.getSerializableExtra("producto") as? Producto

        // Verificar si el producto y su nombre no son nulos
        if (producto != null && !nombreproducto.isNullOrEmpty()) {
            // Crear un mapa con los nuevos datos
            val nuevosDatos = mapOf(
                "nombreProducto" to nombreproducto,
                "descripcion" to descripcion,
                "lote" to numLote,
                "fechaVencimiento" to fecha,
                "ubicacion" to ubicacion,
                "stock" to stock
                // Agrega otros campos y valores que desees actualizar
            )

            // Obtener el nombre del producto a editar
            val nombreProductoAEditar = producto.NombreProducto.toString()

            // Obtener referencia al nodo de productos
            val nodoProducto = "informacion producto"

            // Actualizar datos en Firebase
            databaseReference.child(nodoProducto).child(nombreProductoAEditar)
                .updateChildren(nuevosDatos)
                .addOnSuccessListener {
                    // Los datos se actualizaron exitosamente
                    Toast.makeText(
                        this@ActualizarProductosActivity,
                        "Producto actualizado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Puedes agregar aquí cualquier lógica adicional después de la actualización
                }
                .addOnFailureListener { exception ->
                    // Maneja los errores aquí
                    Toast.makeText(
                        this@ActualizarProductosActivity,
                        "Error al actualizar producto: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            // Producto o nombre del producto es nulo o vacío
            Toast.makeText(this@ActualizarProductosActivity, "Datos de producto inválidos", Toast.LENGTH_SHORT).show()
        }
    }


    fun eliminarDatoSeleccionad(){
        val producto = intent.getSerializableExtra("producto") as? Producto
        val productoAeliminar = producto?.NombreProducto.toString()
        // Referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().reference
        val nodoInformacionProducto = databaseReference.child("Informacion producto")

        // Obtener referencia al nodo del producto que deseas eliminar
        val nodoProductoAEliminar = nodoInformacionProducto.child(productoAeliminar)

        // Eliminar el nodo del producto
        nodoProductoAEliminar.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
            }

    }

    fun getFechaDatePicker(): String {
        var dia = datePicker_actualizarFechaVencimiento?.dayOfMonth.toString().padStart(2, '0')
        var mes = (datePicker_actualizarFechaVencimiento!!.month + 1).toString().padStart(2, '0')
        var anio = datePicker_actualizarFechaVencimiento?.year.toString().padStart(4, '0')

        return "$dia/$mes/$anio"
    }

    fun muestraCalendario(view: View) {
        datePicker_actualizarFechaVencimiento?.visibility = View.VISIBLE
    }


    fun posicionamientoTxtView() {
        var position = intent.getIntExtra("position", -1)
        val producto = intent.getSerializableExtra("producto") as? Producto

        // Verifica si se proporcionó la posición y el producto
        if (position != -1 && producto != null) {
            // Aquí puedes acceder a la posición y al producto
            // Por ejemplo, puedes mostrar la información del producto en los EditText para editar
            txtView_nombreActualizarProducto?.text = producto.NombreProducto

            val nombre = producto.NombreProducto.toString()
            txt_actualizarNomrbeProducto?.setText(nombre)
            val despcripcion = producto.descripcion
            txt_actualizarDescripcion?.setText(despcripcion)
            val lote = producto.lote
            txt_actualizarNumero_deLote?.setText(lote)
            val fecha = producto.fechaVencimiento
            txtDate_actualizarFechaVencimiento?.setText(fecha)
            val ubicacion = producto.ubicacion
            txt_actualizarUbicacion_producto?.setText(ubicacion)
            val stock = producto.stock
            txt_stock_actualizarProductos?.setText(stock)

        } else {
            Toast.makeText(this, "no se asigno la posicion", Toast.LENGTH_SHORT).show()
            println("DEL IF de actualizarProductosactivity Producto: ${producto}. Posicion: ${position}")
        }
    }
}