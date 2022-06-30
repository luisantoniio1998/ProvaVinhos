package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Sales(
    var cliente : Clients,
    var vinho : Wine,
    var quantidade: Long,
    var preco : Double,
    var id: Long = -1
):Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()


        valores.put(TabelaBDVendas.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaBDVendas.CAMPO_PRECO, preco)
        valores.put(TabelaBDVendas.CAMPO_ID_CLIENTE, cliente.id)
        valores.put(TabelaBDVendas.CAMPO_ID_VINHO, vinho.id)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Sales {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posQtd = cursor.getColumnIndex(TabelaBDVendas.CAMPO_QUANTIDADE)
            val posPreco = cursor.getColumnIndex(TabelaBDVendas.CAMPO_PRECO)
            val posIdClient = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_CLIENTE)
            val posIdVinho = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_VINHO)

            val posNomCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NOME)
            val posNifCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NIF)
            val posTelemovelCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_CONTACTO)


            val posNomVinho = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_NOME_VINHO)
            val posPrecGarrafa = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_PRECO_GARRAFA)
            val posStock = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_STOCK)
            val posIDReg = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_ID_REGIAO)
            val posNomeRegiao = cursor.getColumnIndex(TabelaBDRegiao.CAMPO_NOME)


            val nomeRegiao = cursor.getString(posNomeRegiao)
            val idRegiao = cursor.getLong(posIDReg)
            val region = Region(nomeRegiao, idRegiao)

            val nomeVinho = cursor.getString(posNomVinho)
            val precoGarrafa = cursor.getDouble(posPrecGarrafa)
            val stock = cursor.getLong(posStock)

            val idVinho = cursor.getLong(posIdVinho)
            val vinho = Wine(nomeVinho, precoGarrafa, stock, region, idVinho)

            val id = cursor.getLong(posId)
            val quantidade = cursor.getLong(posQtd)
            val preco = cursor.getDouble(posPreco)
            val idCliente = cursor.getLong(posIdClient)


            val nomeCliente = cursor.getString(posNomCliente)
            val nifCliente = cursor.getString(posNifCliente)
            val contactoCliente = cursor.getString(posTelemovelCliente)
            val cliente = Clients(nomeCliente, contactoCliente, nifCliente, idCliente )

            return Sales(cliente, vinho, quantidade, preco, id )

        }


    }
}