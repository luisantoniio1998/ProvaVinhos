package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import androidx.appcompat.app.ActionBar

data class Wine(

    var nomeVinho : String,
    var precoGarrafa : Double,
    var nomeRegiao : String,
    var stock : Long,
    var idRegiao : Long,
    var id: Long = -1
) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVinhos.CAMPO_NOME_VINHO, nomeVinho)
        valores.put(TabelaBDVinhos.CAMPO_PRECO_GARRAFA, precoGarrafa)
        valores.put(TabelaBDVinhos.CAMPO_NOME_REGIAO, nomeRegiao)
        valores.put(TabelaBDVinhos.CAMPO_STOCK, stock)
        valores.put(TabelaBDVinhos.CAMPO_ID_REGIAO, idRegiao)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Wine {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_NOME_VINHO)
            val posPrecoGarrafa = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_PRECO_GARRAFA)
            val posNomeRegiao = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_NOME_REGIAO)
            val posStock = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_STOCK)
            val posIDReg = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_ID_REGIAO)

            val id = cursor.getLong(posId)
            val nomeVinho = cursor.getString(posNome)
            val precoGarrafa = cursor.getDouble(posPrecoGarrafa)
            val nomeRegiao = cursor.getString(posNomeRegiao)
            val stock = cursor.getLong(posStock)
            val idRegiao = cursor.getLong(posIDReg)



            return Wine(nomeVinho, precoGarrafa, nomeRegiao, stock, idRegiao, id)
        }
    }
}