package com.example.fxeventosinventario

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MyAdapter(private var productoList : ArrayList<Producto>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var databaseReference : DatabaseReference
    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item_productos, parent, false)

        return MyViewHolder(itemView)
    }

    fun actualizarLista(nuevaLista: ArrayList<Producto>) {
        productoList.clear()
        productoList.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onUpdateClick(position: Int, producto: Producto)
        fun onDeleteClick(position: Int)
    }

    fun setFilteredList(productoList: ArrayList<Producto>){
        this.productoList = productoList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productoList.size
    }

    fun obtenerProductoEnPosicion(position: Int): Producto {
        return productoList[position]
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productoList[position]

        holder.txtNombreProducto.text = currentItem.NombreProducto
        holder.txtDescripcionProducto.text = currentItem.descripcion
        holder.txtFechaVencimiento.text = currentItem.fechaVencimiento
        holder.txtNumeroLote.text = currentItem.lote
        holder.txtUbicacionProducto.text = currentItem.ubicacion
        holder.txtStockProducto.text = currentItem.stock

        holder.btn_actualizar.setOnClickListener(){
            //position: indice del cardview seleccionado
            //currentItem: el ID del producto con el que se quiere interactuar
            listener.onUpdateClick(position, currentItem)
        }

        holder.btn_eliminar.setOnClickListener(){
            holder.eliminarProducto(position)
        }

    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        //para que funcione todo lo del cardview
        val txtNombreProducto : TextView = itemView.findViewById(R.id.txt_VerNomrbeProducto)
        val txtDescripcionProducto : TextView = itemView.findViewById(R.id.txt_VerDescripcion)
        val txtNumeroLote : TextView = itemView.findViewById(R.id.txt_VerNumeroLote)
        val txtFechaVencimiento : TextView = itemView.findViewById(R.id.txt_VerFechaVencimiento)
        val txtUbicacionProducto : TextView = itemView.findViewById(R.id.txt_VerUbicacion)
        val txtStockProducto : TextView = itemView.findViewById(R.id.txt_VerStock)

        val btn_actualizar : Button = itemView.findViewById(R.id.btn_actualizarProducto)
        val btn_eliminar : Button = itemView.findViewById(R.id.btn_eliminarProducto)

        //Funcion eliminar un hijo del padre JSON bd firebase
        fun eliminarProducto(position: Int){
            var resultado = println("Estado actual:")

            resultado.apply {
                println("Detalle del estado primera instancia:")
                println("Primer estado Posicion: $position")
                println("tamaño de productoList: ${productoList.size}")
                // Agrega más propiedades según sea necesario
            }

            if (position >= 0 && position < productoList.size) {
                val nombreProducto = productoList[position].NombreProducto ?: return
                val databaseReference = FirebaseDatabase.getInstance().getReference("informacion producto")

                // Elimina el nodo del producto y todos sus hijos
                databaseReference.child(nombreProducto).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(
                            itemView.context,
                            "Producto eliminado: $nombreProducto",
                            Toast.LENGTH_SHORT
                        ).show()

                        // No es necesario eliminar manualmente el elemento de la lista
                        // notifyItemRemoved(position)
                        notifyDataSetChanged()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            itemView.context,
                            "Error al eliminar el producto: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                // Muestra un mensaje indicando que la posición no es válida
                Toast.makeText(itemView.context, "Posición no válida", Toast.LENGTH_SHORT).show()
            }

            resultado.apply {
                println("Detalle del estado:")
                println("Posicion(actualizada): $position")
                println("segundo tamaño de productoList: ${productoList.size}")
                // Agrega más propiedades según sea necesario
            }
        }

    }

}