package com.example.provavinhos.BD

import android.content.ContentValues

data class Clients(
    var id: Long,
    var nome: String,
    var contacto: Long,
    var nif: Long,
    var codigo_postal: String
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDClientes.CAMPO_NOME, nome)
        valores.put(TabelaBDClientes.CAMPO_CONTACTO, contacto )
        valores.put(TabelaBDClientes.CAMPO_NIF, nif)
        valores.put(TabelaBDClientes.CAMPO_CODIGO_POSTAL, codigo_postal)

        return valores
    }
}