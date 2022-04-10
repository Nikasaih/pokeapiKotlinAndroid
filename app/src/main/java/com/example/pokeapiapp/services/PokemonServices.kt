package com.example.pokeapiapp.services

import com.example.pokeapiapp.data.PokemonData
import com.example.pokeapiapp.data.PokemonSize
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

val typesMap: MutableMap<String, String> = mutableMapOf()

fun initializeTypeMap(){
    typesMap.put("bug","https://www.pokepedia.fr/images/thumb/e/ee/Miniature_Type_Insecte_EB.png/68px-Miniature_Type_Insecte_EB.png")
    typesMap.put("steel","https://www.pokepedia.fr/images/thumb/b/b9/Miniature_Type_Acier_EB.png/68px-Miniature_Type_Acier_EB.png")
    typesMap.put("fire","https://www.pokepedia.fr/images/thumb/f/fc/Miniature_Type_Feu_EB.png/68px-Miniature_Type_Feu_EB.png")
    typesMap.put("grass","https://www.pokepedia.fr/images/thumb/3/35/Miniature_Type_Plante_EB.png/68px-Miniature_Type_Plante_EB.png")
    typesMap.put("electric","https://www.pokepedia.fr/images/thumb/6/6c/Miniature_Type_%C3%89lectrik_EB.png/68px-Miniature_Type_%C3%89lectrik_EB.png")
    typesMap.put("psychic","https://www.pokepedia.fr/images/thumb/d/da/Miniature_Type_Psy_EB.png/68px-Miniature_Type_Psy_EB.png")
    typesMap.put("dragon","https://www.pokepedia.fr/images/thumb/2/23/Miniature_Type_Dragon_EB.png/68px-Miniature_Type_Dragon_EB.png")
    typesMap.put("dark","https://www.pokepedia.fr/images/thumb/f/f4/Miniature_Type_T%C3%A9n%C3%A8bres_EB.png/68px-Miniature_Type_T%C3%A9n%C3%A8bres_EB.png")
    typesMap.put("ground","https://www.pokepedia.fr/images/thumb/d/d6/Miniature_Type_Sol_EB.png/68px-Miniature_Type_Sol_EB.png")
    typesMap.put("normal","https://www.pokepedia.fr/images/thumb/2/2e/Miniature_Type_Normal_EB.png/68px-Miniature_Type_Normal_EB.png")
    typesMap.put("fighting","https://www.pokepedia.fr/images/thumb/1/1c/Miniature_Type_Combat_EB.png/68px-Miniature_Type_Combat_EB.png")
    typesMap.put("flying","https://www.pokepedia.fr/images/thumb/6/62/Miniature_Type_Vol_EB.png/68px-Miniature_Type_Vol_EB.png")
    typesMap.put("poison","https://www.pokepedia.fr/images/thumb/2/28/Miniature_Type_Poison_EB.png/68px-Miniature_Type_Poison_EB.png")
    typesMap.put("rock","https://www.pokepedia.fr/images/thumb/d/d3/Miniature_Type_Roche_EB.png/68px-Miniature_Type_Roche_EB.png")
    typesMap.put("ghost","https://www.pokepedia.fr/images/thumb/e/e5/Miniature_Type_Spectre_EB.png/68px-Miniature_Type_Spectre_EB.png")
    typesMap.put("water","https://www.pokepedia.fr/images/thumb/4/4c/Miniature_Type_Eau_EB.png/68px-Miniature_Type_Eau_EB.png")
    typesMap.put("ice","https://www.pokepedia.fr/images/thumb/7/7e/Miniature_Type_Glace_EB.png/68px-Miniature_Type_Glace_EB.png")
    typesMap.put("fairy","https://www.pokepedia.fr/images/thumb/3/3e/Miniature_Type_F%C3%A9e_EB.png/68px-Miniature_Type_F%C3%A9e_EB.png")
}

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