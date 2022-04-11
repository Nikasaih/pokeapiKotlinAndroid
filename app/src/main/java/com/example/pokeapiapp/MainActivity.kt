package com.example.pokeapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapiapp.data.PokemonData
import com.example.pokeapiapp.data.PokemonViewModel
import com.example.pokeapiapp.databinding.ActivityMainBinding
import com.example.pokeapiapp.services.initializeTypeMap
import com.example.pokeapiapp.services.typesMap
import com.squareup.picasso.Picasso

private val TAG: String = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val model by lazy { ViewModelProvider(this).get(PokemonViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initializeTypeMap()
        Log.d(TAG, typesMap.toString() + "typesmap")

        setContentView(binding.root)

        model.data.observe(this) {
            Log.d(TAG, " \n\n$it query")
            changePokemon(it)
        }
        model.loadPokemon("lucario")

    }

    private fun changePokemon(it: PokemonData?) {
        if (it == null) {
            Log.d(TAG, "loading failure")
            return
        }
        binding.pokemonName.text = it.name
        Picasso.get().load(it.imgSrc).into(binding.pokemonImg)

        binding.heightText.text = it.size.height.toString()
        binding.weightText.text = it.size.weight.toString()

        val type1 :String= it.types[0]
        val type1ImgSrc:String? = typesMap[type1]
        if(type1ImgSrc == null)
        {
            Log.d(TAG, it.types[0])
        }
        Log.d(TAG, type1ImgSrc + "cocuo" + it.types[0])

        Picasso.get().load(type1ImgSrc).into(binding.pokemonType1)
        if (it.types.size > 1) {
            Picasso.get().load(typesMap.get(it.types[1])).into(binding.pokemonType2)
        }
    }


}