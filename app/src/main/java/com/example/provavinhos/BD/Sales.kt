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
    var region : Region,
    var id: Long = -1
):Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDVendas.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaBDVendas.CAMPO_PRECO, preco)
        valores.put(TabelaBDVendas.CAMPO_ID_CLIENTE, cliente.id)
        valores.put(TabelaBDVendas.CAMPO_ID_VINHO, vinho.id)
        valores.put(TabelaBDVendas.CAMPO_ID_REGIAO, region.id)

        return valores
    }

    companion object{


        fun fromCursor(cursor: Cursor): Sales {
            val posQuantidade = cursor.getColumnIndex(TabelaBDVendas.CAMPO_QUANTIDADE)
            val posPreco = cursor.getColumnIndex(TabelaBDVendas.CAMPO_PRECO)
            val posIdCliente = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_CLIENTE)
            val posIdVinho = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID_VINHO)
            val posIdVenda = cursor.getColumnIndex(TabelaBDVendas.CAMPO_ID)

            val posNomeCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NOME)
            val posNifCliente = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NIF)
            val posContactoClinte = cursor.getColumnIndex(TabelaBDClientes.CAMPO_CONTACTO)


            val idCliente = cursor.getLong(posIdCliente)
            val nomeCliente= cursor.getString(posNomeCliente)
            val NifCliente = cursor.getString(posNifCliente)
            val ContactoCliente = cursor.getString(posContactoClinte)

            val cliente = Clients(nomeCliente, ContactoCliente, NifCliente, idCliente)

            val posNomeVinho = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_NOME_VINHO)
            val posPrecoGarrafa = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_PRECO_GARRAFA)
            val posStock = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_STOCK)
            val posIdRegiao = cursor.getColumnIndex(TabelaBDVinhos.CAMPO_ID_REGIAO)
            val posnomeRegiao = cursor.getColumnIndex(TabelaBDRegiao.CAMPO_NOME)

            val idRegiao = cursor.getLong(posIdRegiao)
            val nomeRegiao = cursor.getString(posnomeRegiao)
            val nomeVinho = cursor.getString(posNomeVinho)
            val precoGarrafa = cursor.getDouble(posPrecoGarrafa)
            val idVinho = cursor.getLong(posIdVinho)
            val stock = cursor.getLong(posStock)

            val regiao = Region(nomeRegiao, idRegiao)
            val vinho = Wine(nomeVinho, precoGarrafa, stock, regiao, idVinho)

            val vinhoQuantidade = cursor.getLong(posQuantidade)
            val vendaPreco = cursor.getDouble(posPreco)
            val idVenda = cursor.getLong(posIdVenda)

            return Sales(vinhoQuantidade, vendaPreco, cliente, vinho, regiao, idVenda)

        }
    }
}