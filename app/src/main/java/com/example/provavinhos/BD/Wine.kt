package com.example.provavinhos.BD

import android.content.ContentValues
import androidx.appcompat.app.ActionBar

data class Wine(
    var nome: String,
    var stock: Long,
    var ano: String,
    var preco : Double,
    var regiao: String,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVinhos.CAMPO_NOME, nome)
        valores.put(TabelaBDVinhos.CAMPO_STOCK, stock )
        valores.put(TabelaBDVinhos.CAMPO_ANO, ano)
        valores.put(TabelaBDVinhos.CAMPO_PRECO, preco)
        valores.put(TabelaBDVinhos.CAMPO_REGION, regiao)

        return valores
    }
}