package com.example.pokeapiapp.services

import com.example.pokeapiapp.data.PokemonData
import com.example.pokeapiapp.data.PokemonSize
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

fun queryPokemon(pokemonName: String): PokemonData {
    val searchResponse: MutableMap<String, String> = searchPokemon(pokemonName)
    val jsonResponse: String = searchResponse.get("jsonResponse").orEmpty()
    val pokemonSearched: String = searchResponse.get("pokemonName").orEmpty()
    if (jsonResponse == "" || pokemonSearched == "") {
        throw Exception("some error occurred")
    }

    val pokemonData: PokemonData = getPokemonData(pokemonSearched, jsonResponse)
    return pokemonData
}

fun searchPokemon(pokemonName: String): MutableMap<String, String> {
    val pokemonSearched = pokemonName.lowercase()

    val requestUrl: String = "https://pokeapi.co/api/v2/pokemon/$pokemonSearched"
    val jsonResponse: String = RequestUtils.sendGet(requestUrl)

    val responseMap: MutableMap<String, String> = mutableMapOf()
    responseMap.put("pokemonName", pokemonSearched)
    responseMap.put("jsonResponse", jsonResponse)
    return responseMap
}

fun getAbilityTypes(jsonObject: JsonObject): List<String> {
    val abilitiesJson = jsonObject.get("types").asJsonArray

    return abilitiesJson.map { ability ->
        ability.asJsonObject
            .get("type").asJsonObject
            .get("name").toString()
    }
}

fun getPokemonData(pokemonSearched: String, jsonResponse: String): PokemonData {
    val jsonParser = JsonParser()

    val jsonObject: JsonObject = jsonParser.parse(jsonResponse).asJsonObject

    val pokemonTypes: List<String> = getAbilityTypes(jsonObject)
    val pokemonId: Int = jsonObject.get("id").asInt
    val pokemonSize: PokemonSize = Gson().getAdapter(PokemonSize::class.java).fromJson(jsonResponse)

    return PokemonData(pokemonSearched, pokemonId, pokemonSize, pokemonTypes)
}