package com.example.fxeventosinventario

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

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
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val txtNombreProducto : TextView = itemView.findViewById(R.id.txt_VerNomrbeProducto)
        val txtDescripcionProducto : TextView = itemView.findViewById(R.id.txt_VerDescripcion)
        val txtNumeroLote : TextView = itemView.findViewById(R.id.txt_VerNumeroLote)
        val txtFechaVencimiento : TextView = itemView.findViewById(R.id.txt_VerFechaVencimiento)
        val txtUbicacionProducto : TextView = itemView.findViewById(R.id.txt_VerUbicacion)
        val txtStockProducto : TextView = itemView.findViewById(R.id.txt_VerStock)
    }
}