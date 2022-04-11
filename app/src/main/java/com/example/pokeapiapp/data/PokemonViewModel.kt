package com.example.pokeapiapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapiapp.services.queryPokemon
import kotlin.concurrent.thread

private val TAG: String = PokemonViewModel::class.java.simpleName
class PokemonViewModel : ViewModel() {
    val data: MutableLiveData<PokemonData?> = MutableLiveData()

    fun loadPokemon(pokemonName : String) {
        thread {
            try {
                data.postValue(  queryPokemon(pokemonName))
                Log.d(TAG, "query success")

            } catch (e: Exception) {
                Log.d(TAG, "\n \n $e")
            }
        }
    }
}