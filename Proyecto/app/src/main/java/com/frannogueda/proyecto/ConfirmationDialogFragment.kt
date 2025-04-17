package com.frannogueda.proyecto

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment (var puntaje: Int, var totalPreguntas: Int) : DialogFragment()  {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setTitle("Tu puntaje fue: $puntaje / $totalPreguntas")
            .setMessage("Aca vamos a decir si se utilizo o no el comodin")
            .setPositiveButton("volver a jugar") { dialog, which ->
                // acciones a realizar cuando se presiona Confirmar
            }
            .setNegativeButton("volver al menu principal") { dialog, which ->
                activity?.finish()
            }
            .create()
    }
}
