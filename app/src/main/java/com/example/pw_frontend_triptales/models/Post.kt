package com.example.pw_frontend_triptales.models

data class Post(
    val id: Int,
    val utente: Int,
    val gruppo: Int,
    val titolo: String,
    val testo: String?,
    val immagine_path: String?,
    val data_creazione: String,
    val latitudine: Double?,
    val longitudine: Double?,
    val testo_OCR: String?,
    val testo_tradotto: String?,
    val tags_oggetti: String?,
    val didascalia_automatica: String?,
    val posts: List<Post> = emptyList()
)