package com.example.pokeapiapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapiapp.services.initializeTypeMap
import com.example.pokeapiapp.services.queryPokemon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeTypeMap()
        setContentView(R.layout.activity_main)
        changePokemon("rayquaza")
        /*
        try {
            changePokemon("lucario")

        } catch (e: Exception) {
            println("\n\n")
            println(e)
            println("\n\n")
        }*/
    }

    private fun changePokemon(pokemonName :String ) {
        val pokemonFound = queryPokemon(pokemonName)
        val pokemonImg = findViewById<ImageView>(R.id.pokemonImg)

        val imgUri = Uri.parse(pokemonFound.imgSrc)
        val imgType = findViewById<ImageView>(R.id.pokemonType1)

        val imgTypeUri = Uri.parse(pokemonFound.types[0])
        imgType.setImageURI(imgTypeUri)

        if (pokemonFound.types.size > 1) {
            val imgType2 = findViewById<ImageView>(R.id.pokemonType2)
            val imgTypeUri2 = Uri.parse(pokemonFound.types[1])
            imgType2.setImageURI(imgTypeUri2)
        }
        findViewById<TextView>(R.id.heightText).setText(pokemonFound.size.height)
        findViewById<TextView>(R.id.weightText).setText(pokemonFound.size.weight)

        pokemonImg.setImageURI(imgUri)
    }
}