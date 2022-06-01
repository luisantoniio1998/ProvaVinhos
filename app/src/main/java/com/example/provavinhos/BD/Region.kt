package com.example.provavinhos.BD

import android.content.ContentValues

data class Region(
    var nome: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDRegiao.CAMPO_NOME, nome)

        return valores
    }
}