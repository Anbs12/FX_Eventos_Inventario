package com.example.fxeventosinventario.ui.ver_productos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

class VerProductosFragment : Fragment() {

    private var _binding: FragmentVerProductosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dbref : DatabaseReference

    private lateinit var txt_Buscar : SearchView
    private lateinit var productoRecyclerView : RecyclerView
    private lateinit var productoArrayList : ArrayList<Producto>
    private lateinit var adaptador : MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(VerProductosViewModel::class.java)

        _binding = FragmentVerProductosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //para recyclerview
        productoRecyclerView = root.findViewById(R.id.listaProductos_recyclerView)
        txt_Buscar = root.findViewById(R.id.txtBuscador)

        productoRecyclerView.layoutManager = LinearLayoutManager(activity)
        productoRecyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf<Producto>()
        adaptador = MyAdapter(productoArrayList)
        productoRecyclerView.adapter = adaptador
        getProductoData()


        txt_Buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


        return root
    }

    private fun filterList(query : String?){
        if (query != null){
            val listaFiltrada = ArrayList<Producto>()
            for (i in productoArrayList){
                if(i.NombreProducto!!.lowercase(Locale.ROOT).contains((query))){
                    listaFiltrada.add(i)
                }
            }

            if(listaFiltrada.isEmpty()){
                Toast.makeText(activity, "No se encontraron datos", Toast.LENGTH_SHORT).show()
            }else{
                //adaptador.setFilteredList(listaFiltrada)
                productoRecyclerView.adapter = MyAdapter(listaFiltrada)
            }
        }
    }

    private fun getProductoData() {

        dbref = FirebaseDatabase.getInstance().getReference("informacion producto")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    productoArrayList.clear()

                    for (productoSnapshot in snapshot.children){
                        val producto = productoSnapshot.getValue(Producto::class.java)
                        productoArrayList.add(producto!!)
                    }

                    productoRecyclerView.adapter = MyAdapter(productoArrayList)

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
}