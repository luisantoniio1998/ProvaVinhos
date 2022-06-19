package com.example.provavinhos.BD

import android.content.ContentValues

data class Sales(
    var clienteId : Long,
    var vinhoId : Long,
    var nomeCliente : String,
    var nomeVinho: String,
    var quantidade: Long,
    var precoGarrafa : Double,
    var preco : Double,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVendas.CAMPO_ID_CLIENTE, clienteId)
        valores.put(TabelaBDVendas.CAMPO_ID_VINHO, vinhoId)
        valores.put(TabelaBDVendas.CAMPO_NOME_CLIENTE, nomeCliente)
        valores.put(TabelaBDVendas.CAMPO_NOME_VINHO, nomeVinho)
        valores.put(TabelaBDVendas.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaBDVendas.CAMPO_PRECO_GARRAFA, precoGarrafa)
        valores.put(TabelaBDVendas.CAMPO_PRECO, preco)

        return valores
    }
}