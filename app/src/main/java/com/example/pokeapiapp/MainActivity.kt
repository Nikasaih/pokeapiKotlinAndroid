package com.example.pokeapiapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapiapp.services.queryPokemon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        try {

            changePokemon()
          val pokemonFound = queryPokemon("lucario")
            //val pokemonImg = findViewById<ImageView>(R.id.pokemonImg)
findViewById<TextView>(R.id.heightText).setText("hbjbbbuh")
            //val imgUri = Uri.parse(pokemonFound.imgSrc)
           // pokemonImg.setImageURI(imgUri)

        } catch (e: Exception) {
            println(e)
        }
    }

    fun changePokemon() {

    }
}