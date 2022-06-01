package com.example.provavinhos.BD

import android.content.ContentValues

data class Wine(
    var nome: String,
    var stock: Long,
    var ano: Long,
    var regiao: String,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVinhos.CAMPO_NOME, nome)
        valores.put(TabelaBDVinhos.CAMPO_STOCK, stock )
        valores.put(TabelaBDVinhos.CAMPO_ANO, ano)
        valores.put(TabelaBDVinhos.CAMPO_REGION, regiao)

        return valores
    }
}