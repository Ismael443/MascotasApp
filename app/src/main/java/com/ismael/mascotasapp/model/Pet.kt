package com.ismael.mascotasapp.model

data class Pet(
    var name: String,
    var breed: String,
    var age: Int,
    var imageURL: String
){
    constructor() : this("", "", 0, "")
}

