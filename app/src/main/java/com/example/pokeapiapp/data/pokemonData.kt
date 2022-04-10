package com.example.pokeapiapp.data

data class PokemonData(
    var name: String,
    var id: Int,
    var size: PokemonSize,
    var types: List<String>,
) {
    var imgSrc: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}

data class PokemonSize(
    var height: Int,
    var weight: Int
)