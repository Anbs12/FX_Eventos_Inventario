package com.example.fxeventosinventario

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<SwitchPreferenceCompat>("dark_mode")?.setOnPreferenceChangeListener { _, newValue ->
            val isDarkModeEnabled = newValue as Boolean

            // Aplicar lógica para cambiar al modo oscuro según el valor de isDarkModeEnabled
            if (isDarkModeEnabled) {
                // Modo oscuro activado
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Modo oscuro desactivado
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Reiniciar la actividad para que los cambios surtan efecto
            requireActivity().recreate()

            true // Devolver true para aceptar el cambio
        }
    }

}