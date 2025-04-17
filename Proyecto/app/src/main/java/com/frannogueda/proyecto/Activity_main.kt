package com.frannogueda.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import org.json.JSONArray
import java.io.InputStream

class Activity_main : AppCompatActivity() {
    var questionList: ArrayList<Question> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun loadQuestionsFromJSON(name:String) {
        val inputStream: InputStream = assets.open(name+".json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val text = String(buffer, Charsets.UTF_8)
        val jsonArray = JSONArray(text)
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            val question = jsonObject.getString("question")
            val optionsArray = jsonObject.getJSONArray("options")
            val options = ArrayList<String>()
            for (j in 0..optionsArray.length() - 1) {
                options.add(optionsArray.getString(j))
            }
            val correctAnswerIndex = jsonObject.getInt("correctAnswerIndex")
            val q =  Question(question, options, correctAnswerIndex)
            questionList.add(q)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi: MenuInflater = menuInflater;
        mi.inflate(R.menu.action_bar_menuppal, menu);
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var i = Intent(this,Activity_ayuda::class.java)
        startActivity(i)
        /*if (item.itemId == R.id.Ayuda){
        }No es necesario el identificador pq es una sola cosa que se ejecuta */
        return true;
    }
    fun iniciar(v: View){
        var boton: Button = findViewById(v.id)
        var categoria = boton.text.toString()
        loadQuestionsFromJSON(categoria)
        var i = Intent(this, Activity_preguntas::class.java)
        i.putExtra("categoria",categoria)
        i.putExtra("dato",questionList)
        startActivity(i)
        questionList.clear()  // Lo utilizamos para que se limpie el arrayList y no conserve las preguntas anteriores
    }
}