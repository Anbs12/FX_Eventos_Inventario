package com.example.fxeventosinventario.ui.lector_qr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fxeventosinventario.R
import com.example.fxeventosinventario.databinding.FragmentLectorQrBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class LectorQrFragment : Fragment() {

    private var _binding: FragmentLectorQrBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(LectorQrViewModel::class.java)

        _binding = FragmentLectorQrBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val boton_lectorQR = view?.findViewById<Button>(R.id.button_lectorQr)

        boton_lectorQR?.setOnClickListener(){
            initScanner()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initScanner() {
        val integrator = IntentIntegrator(activity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el codigo QR!")
        integrator.setOrientationLocked(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent?){
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        Toast.makeText(activity, "Funciona porfavor", Toast.LENGTH_LONG)
        if(result != null){
            if(result.contents == null){
                Toast.makeText(activity, "Cancelado", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(activity, "El valor escaneado es: ${result.contents}", Toast.LENGTH_LONG).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}