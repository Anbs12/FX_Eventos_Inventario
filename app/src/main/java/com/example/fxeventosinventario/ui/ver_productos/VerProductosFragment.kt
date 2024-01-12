package com.example.fxeventosinventario.ui.ver_productos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fxeventosinventario.ActualizarProductosActivity
import com.example.fxeventosinventario.MyAdapter
import com.example.fxeventosinventario.Producto
import com.example.fxeventosinventario.R
import com.example.fxeventosinventario.databinding.FragmentVerProductosBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale


class VerProductosFragment : Fragment(), MyAdapter.OnItemClickListener {

    private lateinit var databaseReference : DatabaseReference
    private var _binding: FragmentVerProductosBinding? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dbref: DatabaseReference

    private lateinit var txt_Buscar: SearchView
    private lateinit var productoRecyclerView: RecyclerView
    private lateinit var productoArrayList: ArrayList<Producto>
    private lateinit var adaptador: MyAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val VerProductosViewModel =
            ViewModelProvider(this).get(VerProductosViewModel::class.java)

        _binding = FragmentVerProductosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //para recyclerview
        productoRecyclerView = root.findViewById(R.id.listaProductos_recyclerView)
        txt_Buscar = root.findViewById(R.id.txtBuscador)
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        databaseReference = FirebaseDatabase.getInstance().reference

        productoRecyclerView.layoutManager = LinearLayoutManager(activity)
        productoRecyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf<Producto>()
        adaptador = MyAdapter(productoArrayList, this)
        productoRecyclerView.adapter = adaptador

        //Obtenemos los datos
        getProductoData()


        //para searchview
        txt_Buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        swipeRefreshLayout.setOnRefreshListener {
            // Lógica para actualizar la lista desde Firebase
            swipeRefreshLayout.isRefreshing = true
            adaptador.MyViewHolder(productoRecyclerView)
            Thread.sleep(500)
            // Finaliza el indicador de actualización
            swipeRefreshLayout.isRefreshing = false
        }

        return root
    }


    private fun filterList(query: String?) {
        if (query != null) {
            val listaFiltrada = ArrayList<Producto>()
            for (i in productoArrayList) {
                if (i.NombreProducto!!.lowercase(Locale.ROOT).contains((query))) {
                    listaFiltrada.add(i)
                }
            }

            if (listaFiltrada.isEmpty()) {
                Toast.makeText(activity, "No se encontraron datos", Toast.LENGTH_SHORT).show()
            } else {
                //Se almacena el producto por el nombre ingresado el cual es el buscado
                productoRecyclerView.adapter = MyAdapter(listaFiltrada, this)
            }
        }
    }

    private fun getProductoData() {

        dbref = FirebaseDatabase.getInstance().getReference("informacion producto")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    productoArrayList.clear()

                    for (productoSnapshot in snapshot.children) {
                        val producto = productoSnapshot.getValue(Producto::class.java)
                        productoArrayList.add(producto!!)
                    }

                    //Aqui se almacena la lista para mostrar en el recyclerview
                    productoRecyclerView.adapter =
                        MyAdapter(productoArrayList, this@VerProductosFragment)

                    println("Array list: ${productoArrayList}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onUpdateClick(position: Int, producto: Producto) {
        // Abre la actividad de actualización con la posición y el producto
        val intent = Intent(requireContext(), ActualizarProductosActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        adaptador.MyViewHolder(productoRecyclerView).eliminarProducto(position)
        adaptador.actualizarLista(productoArrayList)
    }

    private fun cargarListaDesdeFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Limpia el ArrayList
                    productoArrayList.clear()

                    // Itera sobre los nodos para obtener los productos y añadirlos al ArrayList
                    for (productoSnapshot in snapshot.children) {
                        val producto = productoSnapshot.getValue(Producto::class.java)
                        if (producto != null) {
                            productoArrayList.add(producto)
                        }
                    }

                    // Actualiza el RecyclerView con el nuevo ArrayList
                    adaptador.actualizarLista(productoArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja los errores aquí
            }
        })
    }


}