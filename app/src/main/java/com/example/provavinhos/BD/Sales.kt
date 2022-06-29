package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Sales(

    var nomeCliente : String,
    var nomeVinho: String,
    var quantidade: Long,
    var precoGarrafa : Double,
    var preco : Double,
    var idCliente : Long,
    var idVinho : Long,
    var id: Long = -1
):Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()


        valores.put(TabelaBDVendas.CAMPO_NOME_CLIENTE, nomeCliente)
        valores.put(TabelaBDVendas.CAMPO_NOME_VINHO, nomeVinho)
        valores.put(TabelaBDVendas.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaBDVendas.CAMPO_PRECO_GARRAFA, precoGarrafa)
        valores.put(TabelaBDVendas.CAMPO_PRECO, preco)
        valores.put(TabelaBDVendas.CAMPO_ID_CLIENTE, idCliente)
        valores.put(TabelaBDVendas.CAMPO_ID_VINHO, idVinho)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Sales {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomCliente = cursor.getColumnIndex(TabelaBDVendas.CAMPO_NOME_CLIENTE)
            val posNomVinho = cursor.getColumnIndex(TabelaBDVendas.CAMPO_NOME_VINHO)
            val posQtd = cursor.getColumnIndex(TabelaBDVendas.CAMPO_QUANTIDADE)
            val posPrecGarrafa = cursor.getColumnIndex(TabelaBDVendas.CAMPO_PRECO_GARRAFA)
            val posPreco = cursor.getColumnIndex(TabelaBDVendas.CAMPO_PRECO)
            val posIdClient = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_CLIENTE)
            val posIdVinho = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_VINHO)


            val id = cursor.getLong(posId)
            val nomecliente = cursor.getString(posNomCliente)
            val nomeVinho = cursor.getString(posNomVinho)
            val quantidade = cursor.getLong(posQtd)
            val precoGarrafa = cursor.getDouble(posPrecGarrafa)
            val preco = cursor.getDouble(posPreco)
            val idCliente = cursor.getLong(posIdClient)
            val idVinho = cursor.getLong(posIdVinho)

            return Sales(nomecliente, nomeVinho, quantidade, precoGarrafa, preco, idCliente, idVinho, id)

        }


    }
}