package com.example.provavinhos.BD

import android.content.ContentValues

data class Region(
    var id: Long = -1,
    var nome: String,
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDRegiao.CAMPO_NOME, nome)

        return valores
    }
}