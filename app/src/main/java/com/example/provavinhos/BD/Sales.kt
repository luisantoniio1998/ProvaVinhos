package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Sales(
    var quantidade: Long,
    var preco : Double,
    var cliente : Clients,
    var vinho : Wine,
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
           val posNomeCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NOME)
            val posNifCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NIF)
            val posContactoCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_CONTACTO)
            val posIdCliente = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_CLIENTE)

            val nomeCliente = cursor.getString(posNomeCliente)
            val nifCliente = cursor.getString(posNifCliente)
            val contactoCliente = cursor.getString(posContactoCliente)
            val idCliente = cursor.getLong(posIdCliente)

            val cliente = Clients(nomeCliente, contactoCliente, nifCliente, idCliente)

            val posnomeRegion = cursor.getColumnIndex(TabelaBDRegiao.CAMPO_NOME)
            val posidRegion = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_ID_REGIAO)

            val posIdVinho = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_VINHO)
            val posnomeVinho = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_NOME_VINHO)
            val posstock = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_STOCK)
            val posprecoGarrafa = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_PRECO_GARRAFA)

            val nomeRegion = cursor.getString(posnomeRegion)
            val idRegiao = cursor.getLong(posidRegion)
            val regiao = Region(nomeRegion, idRegiao)


            val nomeVinho = cursor.getString(posnomeVinho)
            val stock = cursor.getLong(posstock)
            val precoGarrafa = cursor.getDouble(posprecoGarrafa)
            val idVinho = cursor.getLong(posIdVinho)

            val vinho = Wine( nomeVinho, precoGarrafa, stock, regiao, idVinho)

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posQuantidade = cursor.getColumnIndex(TabelaBDVendas.CAMPO_QUANTIDADE)
            val posPreco = cursor.getColumnIndex(TabelaBDVendas.CAMPO_PRECO)

            val id = cursor.getLong(posId)
            val quantidade = cursor.getLong(posQuantidade)
            val preco = cursor.getDouble(posPreco)

            return Sales(quantidade, preco, cliente, vinho ,  id)

        }
    }
}