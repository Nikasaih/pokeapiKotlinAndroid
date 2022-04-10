package com.example.pokeapiapp.services

import okhttp3.OkHttpClient
import okhttp3.Request

val client = OkHttpClient()

class RequestUtils {
    companion object {
        fun sendGet(url: String): String {
            println("url : $url")
            //Création de la requete
            val request = Request.Builder().url(url).build()
            //Execution de la requête
            return client.newCall(request).execute().use {
                //Analyse du code retour
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                //Résultat de la requete
                it.body?.string() ?: ""
            }
        }
    }
}