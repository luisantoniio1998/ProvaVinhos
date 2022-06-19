package com.example.provavinhos.BD

import android.content.ContentValues

data class Clients(
    var nome: String,
    var contacto: String,
    var nif: String,
    var id: Long =-1
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDClientes.CAMPO_NOME, nome)
        valores.put(TabelaBDClientes.CAMPO_CONTACTO, contacto )
        valores.put(TabelaBDClientes.CAMPO_NIF, nif)

        return valores
    }
}