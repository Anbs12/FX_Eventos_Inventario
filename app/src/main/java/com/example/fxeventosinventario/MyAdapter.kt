package com.example.fxeventosinventario

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fxeventosinventario.ActualizarProductosActivity


class MyAdapter(private var productoList : ArrayList<Producto>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item_productos, parent, false)

        return MyViewHolder(itemView)
    }

    fun setFilteredList(productoList: ArrayList<Producto>){
        this.productoList = productoList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productoList.size
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
            val intent = Intent(holder.itemView.context, ActualizarProductosActivity::class.java)
            holder.itemView.context.startActivity(intent)
            
            Toast.makeText(holder.itemView.context, "Actualiza el producto seleccionado", Toast.LENGTH_SHORT).show()

        }

        holder.btn_eliminar.setOnClickListener(){
            Toast.makeText(holder.itemView.context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        //para que funcione todo lo del cardview
        val txtNombreProducto : TextView = itemView.findViewById(R.id.txt_VerNomrbeProducto)
        val txtDescripcionProducto : TextView = itemView.findViewById(R.id.txt_VerDescripcion)
        val txtNumeroLote : TextView = itemView.findViewById(R.id.txt_VerNumeroLote)
        val txtFechaVencimiento : TextView = itemView.findViewById(R.id.txt_VerFechaVencimiento)
        val txtUbicacionProducto : TextView = itemView.findViewById(R.id.txt_VerUbicacion)
        val txtStockProducto : TextView = itemView.findViewById(R.id.txt_VerStock)

        val btn_actualizar : Button = itemView.findViewById(R.id.btn_actualizarProducto)
        val btn_eliminar : Button = itemView.findViewById(R.id.btn_eliminarProducto)


    }
}