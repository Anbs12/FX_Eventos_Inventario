package com.example.fxeventosinventario.ui.crear_productos

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.fxeventosinventario.Producto
import com.example.fxeventosinventario.R
import com.example.fxeventosinventario.databinding.FragmentCrearProductosBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CrearProductosFragment : Fragment() {

    var txtDate_fechaVencimiento : EditText?= null
    var button_FechaVencimiento : ImageButton?= null
    var button_ingresar_producto: ImageButton?=null
    var datePicker_fechaVencimiento : DatePicker?= null
    //private lateinit var txt_nombre: EditText
    private lateinit var databaseReference: DatabaseReference

    private var _binding: FragmentCrearProductosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCrearProductosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imagen_subida = root.findViewById<ImageView>(R.id.imagen_crear_producto)
        val button_ingresar_producto = root.findViewById<Button>(R.id.button_crear_producto)

        /*val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = requireActivity().findViewById<NavigationView>(R.id.nav_view)
        // Aquí puedes hacer lo que quieras con el navbar, como abrirlo o cerrarlo
        drawerLayout.openDrawer(navView)*/


        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == Activity.RESULT_OK){
                val intent = result.data
                val imageBitMap = intent?.extras?.get("data") as Bitmap
                val imageView = root.findViewById<ImageView>(R.id.imagen_crear_producto)
                imageView.setImageBitmap(imageBitMap)
            }
        }


        /*val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        // Fecha de Vencimiento funciones
        txtDate_fechaVencimiento = root.findViewById(R.id.txtDate_fechaVencimiento)
        button_FechaVencimiento = root.findViewById(R.id.button_fecha_vencimiento)
        datePicker_fechaVencimiento = root.findViewById(R.id.datePicker_fechaVencimiento)

        txtDate_fechaVencimiento?.setText(getFechaDatePicker())

        //muestra el datepicker calendario
        button_FechaVencimiento?.setOnClickListener {
            muestraCalendario(root)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_fechaVencimiento?.setOnDateChangedListener{
                    datePicker_fechaVencimiento, anio, mes, dia->
                txtDate_fechaVencimiento?.setText(getFechaDatePicker())
                datePicker_fechaVencimiento?.visibility = View.GONE
            }
        }


        //crud subir datos
        button_ingresar_producto.setOnClickListener{

            //val NombreProducto = R.id.txt_nomrbeProducto.toString()
            val NombreProducto = _binding!!.txtNomrbeProducto.text.toString()
            // val descripcion = R.id.txt_descripcionproducto.toString()
            val descripcion =_binding!!.txtDescripcionproducto.text.toString()
            //val lote = R.id.txt_numero_deLote.toString()
            val lote = _binding!!.txtNumeroDeLote.text.toString()
            //val fechaVencimiento = R.id.txtDate_fechaVencimiento.toString()
            val fechaVencimiento = _binding!!.txtDateFechaVencimiento.text.toString()
            //val ubicacion = R.id.txt_ubicacion_producto.toString()
            val ubicacion = _binding!!.txtUbicacionProducto.text.toString()
            //val stock = R.id.txt_stock_productos.toInt()
            val stock = _binding!!.txtStockProductos.text.toString()

            if (_binding!!.txtNomrbeProducto.text.isNullOrBlank() ||
                _binding!!.txtDescripcionproducto.text.isNullOrBlank() ||
                _binding!!.txtNumeroDeLote.text.isNullOrBlank() ||
                _binding!!.txtDateFechaVencimiento.text.isNullOrBlank() ||
                _binding!!.txtUbicacionProducto.text.isNullOrBlank() ||
                _binding!!.txtStockProductos.text.isNullOrBlank()
            ) {
                // Al menos un campo está vacío, mostrar mensaje
                Toast.makeText(activity, "Faltan datos por ingresar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                // Todos los campos están llenos, continuar con el procesamiento de los datos
                //val stockInput = _binding!!.txtStockProductos.text.toString()
                //val stock = stockInput.toInt()

                // Continuar con el procesamiento de los datos...
            }


            databaseReference = FirebaseDatabase.getInstance().getReference("informacion producto")
            val producto = Producto(NombreProducto, descripcion, lote, fechaVencimiento,ubicacion, stock)
            databaseReference.child(NombreProducto).setValue(producto).addOnSuccessListener{
                //R.id.txt_nomrbeProducto.tclear()

                // Limpia los campos de texto después de guardar el producto
                _binding?.txtNomrbeProducto?.setText("")
                _binding?.txtDescripcionproducto?.setText("")
                _binding?.txtNumeroDeLote?.setText("")
                _binding?.txtDateFechaVencimiento?.setText("")
                _binding?.txtUbicacionProducto?.setText("")
                _binding?.txtStockProductos?.setText("")

                // Muestra un mensaje de éxito
                Toast.makeText(activity, "Producto guardado exitosamente", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                // Muestra un mensaje de falla si ocurre un error al guardar el producto
                Toast.makeText(activity, "Hubo un problema al guardar el producto", Toast.LENGTH_SHORT).show()
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getFechaDatePicker():String{
        var dia = datePicker_fechaVencimiento?.dayOfMonth.toString().padStart(2, '0')
        var mes = (datePicker_fechaVencimiento!!.month + 1).toString().padStart(2, '0')
        var anio = datePicker_fechaVencimiento?.year.toString().padStart(4, '0')

        return "$dia/$mes/$anio"
    }

    fun muestraCalendario(view: View){
        datePicker_fechaVencimiento?.visibility = View.VISIBLE
    }
}