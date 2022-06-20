package com.example.provavinhos.BD

import android.content.ContentValues
import androidx.appcompat.app.ActionBar

data class Wine(

    var nomeVinho : String,
    var precoGarrafa : Double,
    var nomeRegiao : String,
    var stock : Long,
    var idRegiao : Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVinhos.CAMPO_NOME_VINHO, nomeVinho)
        valores.put(TabelaBDVinhos.CAMPO_PRECO_GARRAFA, precoGarrafa)
        valores.put(TabelaBDVinhos.CAMPO_NOME_REGIAO, nomeRegiao)
        valores.put(TabelaBDVinhos.CAMPO_STOCK, stock)
        valores.put(TabelaBDVinhos.CAMPO_ID_REGIAO, idRegiao)
        return valores
    }
}