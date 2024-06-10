package com.example.autosell

data class Item(
    var id: String? = null, // Используйте Long вместо String
    var image: String? = null,
    var title: String? = null,
    var model: String? = null, // Поле для модели
    var desc: String? = null,
    var price: Double = 0.0
) {
    constructor() : this("", "", "", "", "", 0.0)
}
