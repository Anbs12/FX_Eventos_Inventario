package com.example.fxeventosinventario.ui.ver_productos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class VerProductosFragment : Fragment() {

    private var _binding: FragmentVerProductosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dbref : DatabaseReference
    private lateinit var productoRecyclerView : RecyclerView
    private lateinit var productoArrayList : ArrayList<Producto>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(VerProductosViewModel::class.java)

        _binding = FragmentVerProductosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        productoRecyclerView = root.findViewById(R.id.listaProductos_recyclerView)
        productoRecyclerView.layoutManager = LinearLayoutManager(activity)
        productoRecyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf<Producto>()
        getProductoData()



        return root
    }

    private fun getProductoData() {

        dbref = FirebaseDatabase.getInstance().getReference("informacion producto")
        println("BASE DE DATO: " + dbref)
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    for (productoSnapshot in snapshot.children){
                        val producto = productoSnapshot.getValue(Producto::class.java)
                        println("REVISAR: " + producto)
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