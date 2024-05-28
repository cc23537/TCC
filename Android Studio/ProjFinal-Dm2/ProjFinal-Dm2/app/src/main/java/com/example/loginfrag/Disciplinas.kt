package com.example.loginfrag

class Disciplinas {
    var ID: String? = null
    var Nome: String? = null
    var Calorias: String? = null


    constructor()

    constructor(id: String?, name: String?, calorias: String?) {
        this.ID = id
        this.Nome = name
       this.Calorias = calorias
    }
}