package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import androidx.appcompat.app.ActionBar
import java.io.Serializable

data class Wine(

    var nomeVinho : String,
    var precoGarrafa : Double,
    var stock : Long,
    var idRegiao : Long,
    var id: Long = -1
): Serializable{
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVinhos.CAMPO_NOME_VINHO, nomeVinho)
        valores.put(TabelaBDVinhos.CAMPO_PRECO_GARRAFA, precoGarrafa)
        valores.put(TabelaBDVinhos.CAMPO_STOCK, stock)
        valores.put(TabelaBDVinhos.CAMPO_ID_REGIAO, idRegiao)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Wine {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_NOME_VINHO)
            val posPrecoGarrafa = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_PRECO_GARRAFA)
            val posStock = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_STOCK)
            val posIDReg = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_ID_REGIAO)

            val id = cursor.getLong(posId)
            val nomeVinho = cursor.getString(posNome)
            val precoGarrafa = cursor.getDouble(posPrecoGarrafa)
            val stock = cursor.getLong(posStock)
            val idRegiao = cursor.getLong(posIDReg)



            return Wine(nomeVinho, precoGarrafa, stock, idRegiao, id)
        }
    }
}