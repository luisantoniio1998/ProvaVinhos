package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Region(
    var nomeRegiao: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDRegiao.CAMPO_NOME, nomeRegiao)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Region {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDRegiao.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Region(nome, id)

        }
    }
}