package com.example.provavinhos.BD

import android.content.ContentValues

data class Sales(
    var id: Long,
    var nome_cliente: String,
    var nome_vinho: String,
    var numero: Long,
    var preco: Long
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVendas.CAMPO_NOME_CLIENTE, nome_cliente)
        valores.put(TabelaBDVendas.CAMPO_NOME_VINHO, nome_vinho )
        valores.put(TabelaBDVendas.CAMPO_NUMERO, numero)
        valores.put(TabelaBDVendas.CAMPO_PRECO, preco)

        return valores
    }
}