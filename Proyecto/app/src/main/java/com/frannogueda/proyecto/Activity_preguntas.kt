package com.frannogueda.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.frannogueda.proyecto.R.color.gris
import com.frannogueda.proyecto.R.color.BotonColor
import com.frannogueda.proyecto.R.color.rojo
import com.frannogueda.proyecto.R.color.verde

class Activity_preguntas : AppCompatActivity() {
    var contador = 0
    var cantPreg = 0
    var Comodin = true
    lateinit var lista: ArrayList<Question>
    lateinit var pregunta: TextView
    lateinit var puntaje: TextView
    lateinit var rta1: Button
    lateinit var rta2: Button
    lateinit var rta3: Button
    lateinit var rta4: Button
    lateinit var comodin: Button
    lateinit var BotonCorrecto: Button
    lateinit var catego:TextView
    lateinit var tiempo:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)
        lista = intent.getSerializableExtra("dato") as ArrayList<Question>
        asignarIds()
        iniciarPregunta(cantPreg)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("ElCantPreg",cantPreg)
        outState.putInt("ElContador",contador)
        outState.putBoolean("ElComodin",Comodin)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle){
        super.onRestoreInstanceState(savedInstanceState)
        cantPreg = savedInstanceState.getInt("ElCantPreg")
        contador = savedInstanceState.getInt("ElContador")
        Comodin = savedInstanceState.getBoolean("ElComodin")
        if (cantPreg <= lista.size - 1) {
            iniciarPregunta(cantPreg)
        } else {
            puntajeFinal()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi: MenuInflater = menuInflater;
        mi.inflate(R.menu.action_bar_juego, menu);
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val i: Intent
        if (item.itemId == R.id.Ayuda){
            i = Intent(this, Activity_ayuda::class.java)
            startActivity(i)
        } else if (item.itemId == R.id.Atras){
            i = Intent(this, Activity_main::class.java)
            startActivity(i)
            // es necesario iniciar una activity? pq no destruir la actual ?
        }
        return true;
    }
    fun asignarIds(){
        val categoria = intent.getStringExtra("categoria")
        tiempo=findViewById(R.id.tiempo)
        catego = findViewById(R.id.categoria)
        catego.setText(categoria.toString())
        pregunta = findViewById(R.id.texto)
        comodin = findViewById(R.id.comodin)
        rta1 = findViewById(R.id.rta1)
        rta2 = findViewById(R.id.rta2)
        rta3 = findViewById(R.id.rta3)
        rta4 = findViewById(R.id.rta4)
        puntaje = findViewById(R.id.puntaje)
    }
    fun iniciarPregunta(i:Int) {
        rta1.text = lista[i].opcion[0]
        rta2.text = lista[i].opcion[1]
        rta3.text = lista[i].opcion[2]
        rta4.text = lista[i].opcion[3]
        pregunta.text = lista[i].pregunta
        if (Comodin) {
            puntaje.setText(contador.toString()+" / "+cantPreg.toString())
        } else {
            val preg: Int = cantPreg - 1
            puntaje.setText(contador.toString()+" / "+preg.toString())
            comodin.setBackgroundColor(getColor(rojo))
        }
    }
    fun jugar(v: View) {
        var cumplio: Boolean
        if (cantPreg < lista.size - 1) {
            cumplio = comparar(v, lista[cantPreg].respuestaCorrecta)
            if (cumplio) {
                contador++
            }
            cantPreg++
            correctoEIncorrecto(v,cumplio)
        } else {
            cumplio = comparar(v, lista[cantPreg].respuestaCorrecta)
            if (cumplio) {
                contador++
            }
            cantPreg++
            correctoEIncorrecto(v,cumplio)
        }
    }
    fun correctoEIncorrecto(v: View, correcto: Boolean) {
        val handler = Handler()
        if (correcto) {
            v.setBackgroundColor(getColor(verde))
        } else {
            v.setBackgroundColor(getColor(rojo))
            BotonCorrecto.setBackgroundColor(getColor(gris))
        }
        handler.postDelayed({
            v.setBackgroundColor(getColor(BotonColor))
            BotonCorrecto.setBackgroundColor(getColor(BotonColor))
            if(cantPreg<=lista.size-1){
                iniciarPregunta(cantPreg)
            }else{
                puntajeFinal()
            }
        }, 2000)
    }
    fun comparar(view: View, a: Int): Boolean {
        var aux: Int = -1
        // para saber que boton selecciono el usuario
        when(view.id) {
            R.id.rta1 -> aux = 0
            R.id.rta2 -> aux = 1
            R.id.rta3 -> aux = 2
            R.id.rta4 -> aux = 3
        }
        // guarda en BotonCorrecto el id de la respuesta correcta
        when(a){
            0-> BotonCorrecto = rta1
            1-> BotonCorrecto = rta2
            2-> BotonCorrecto = rta3
            3-> BotonCorrecto = rta4
        }
        return (a == aux)
    }
    fun comodin(v:View){
        if((Comodin)&&(cantPreg < lista.size-1)){
            cantPreg++
            Comodin = false
            iniciarPregunta(cantPreg)
        } else {
            if((cantPreg == lista.size-1)&&(Comodin)){
                cantPreg++
                Comodin = false
                puntajeFinal()
            }
        }
    }
    fun puntajeFinal(){
        catego.visibility=View.INVISIBLE
        puntaje.visibility = View.INVISIBLE
        comodin.visibility = View.INVISIBLE
        pregunta.visibility = View.INVISIBLE
        tiempo.visibility = View.INVISIBLE
        rta1.visibility = View.INVISIBLE
        rta2.visibility = View.INVISIBLE
        rta3.visibility = View.INVISIBLE
        rta4.visibility = View.INVISIBLE
        if (Comodin) {
            puntaje.setText(contador.toString()+" / "+cantPreg.toString())
        } else {
            cantPreg=cantPreg-1
            puntaje.setText(contador.toString()+" / "+cantPreg.toString())
        }
        dialogo(this.cantPreg)
    }
    private fun dialogo(totalPreg:Int){
        var aux:String
        if(Comodin){
            aux="Este jugador NO uso el Comodin"
        }else{
            aux="Este jugador uso el Comodin"
        }
        val builder=AlertDialog.Builder(this@Activity_preguntas)
            .setTitle("Tu puntaje fue: $contador / $totalPreg")
            .setMessage(aux)
            .setPositiveButton("volver a jugar") { dialog, which ->
                volverAjugar()
            }
            .setNegativeButton("volver al menu principal") { dialog, which ->
                this.finish()
            }
            builder.show()
    }
    private fun volverAjugar(){
        cantPreg = 0
        contador = 0
        Comodin = true
        comodin.setBackgroundColor(getColor(BotonColor))
        puntaje.visibility = View.VISIBLE
        comodin.visibility = View.VISIBLE
        pregunta.visibility = View.VISIBLE
        tiempo.visibility = View.VISIBLE
        rta1.visibility = View.VISIBLE
        rta2.visibility = View.VISIBLE
        rta3.visibility = View.VISIBLE
        rta4.visibility = View.VISIBLE
        catego.visibility = View.VISIBLE
        iniciarPregunta(cantPreg)
    }
}