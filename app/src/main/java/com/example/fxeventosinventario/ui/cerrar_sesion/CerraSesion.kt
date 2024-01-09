package com.example.fxeventosinventario.ui.crear_productos
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fxeventosinventario.R

class CerraSesion : Fragment() {

    companion object {
        fun newInstance() = CerraSesion()
    }

    private lateinit var viewModel: CerraSesionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cerra_sesion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CerraSesionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}