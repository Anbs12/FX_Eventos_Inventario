package com.example.fxeventosinventario

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class ActualizarProductosActivity : AppCompatActivity() {

    //variables de los EditText
    var txtView_nombreActualizarProducto :  TextView ?=null
    var txt_actualizarNomrbeProducto : EditText ?=null
    var txt_actualizarDescripcion : EditText ?=null
    var txt_actualizarNumero_deLote : EditText ?=null
    var txt_actualizarUbicacion_producto : EditText ?=null
    var txt_stock_actualizarProductos : EditText ?=null

    //variable del boton para actualizar
    var button_actualizar_producto : Button ?=null
    var button_actualizarFecha_vencimiento : ImageButton ?=null

    //variables datepicker
    var txtDate_actualizarFechaVencimiento : EditText ?=null
    var datePicker_actualizarFechaVencimiento : DatePicker ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_productos)

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
        datePicker_actualizarFechaVencimiento = findViewById(R.id.datePicker_actualizarFechaVencimiento)

        txtDate_actualizarFechaVencimiento?.setText(getFechaDatePicker())

        button_actualizarFecha_vencimiento?.setOnClickListener {
            muestraCalendario(View(this))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_actualizarFechaVencimiento?.setOnDateChangedListener{
                    datePicker_fechaVencimiento, anio, mes, dia->
                txtDate_actualizarFechaVencimiento?.setText(getFechaDatePicker())
                datePicker_fechaVencimiento?.visibility = View.GONE
            }
        }

        button_actualizar_producto?.setOnClickListener(){
            actualizarProducto()
        }


    }

    private fun actualizarProducto() {
        
    }

    fun getFechaDatePicker():String{
        var dia = datePicker_actualizarFechaVencimiento?.dayOfMonth.toString().padStart(2, '0')
        var mes = (datePicker_actualizarFechaVencimiento!!.month + 1).toString().padStart(2, '0')
        var anio = datePicker_actualizarFechaVencimiento?.year.toString().padStart(4, '0')

        return "$dia/$mes/$anio"
    }

    fun muestraCalendario(view: View){
        datePicker_actualizarFechaVencimiento?.visibility = View.VISIBLE
    }
}