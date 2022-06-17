package com.example.provavinhos.BD

import android.content.ContentValues

data class Sales(

    var nome_cliente: String,
    var nome_vinho: String,
    var preco_garrafa: Double,
    var quantidade: Long,
    var preco: Double,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVendas.CAMPO_NOME_CLIENTE, nome_cliente)
        valores.put(TabelaBDVendas.CAMPO_NOME_VINHO, nome_vinho )
        valores.put(TabelaBDVendas.CAMPO_PRECO_GARRAFA, preco_garrafa)
        valores.put(TabelaBDVendas.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaBDVendas.CAMPO_PRECO, preco)

        return valores
    }
}