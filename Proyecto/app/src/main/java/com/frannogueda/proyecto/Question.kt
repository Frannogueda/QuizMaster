package com.frannogueda.proyecto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Question(question: String, options: ArrayList<String>, correctAnswerIndex: Int):Serializable{
    var pregunta: String = question
    var opcion: ArrayList<String> = options
    var respuestaCorrecta: Int = correctAnswerIndex;

}
