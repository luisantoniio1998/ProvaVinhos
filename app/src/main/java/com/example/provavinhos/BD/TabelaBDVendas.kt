package com.example.provavinhos.BD

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDVendas (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$CAMPO_NOME_CLIENTE TEXT NOT NULL, " +
        "$CAMPO_NOME_VINHO TEXT NOT NULL, " +
        "$CAMPO_QUANTIDADE INTEGER NOT NULL, " +
        "$CAMPO_PRECO_GARRAFA REAL NOT NULL, "  +
        "$CAMPO_PRECO REAL NOT NULL, " +
        "$CAMPO_ID_CLIENTE INTEGER NOT NULL, " +
        "$CAMPO_ID_VINHO INTEGER NOT NULL, " +
        "FOREIGN KEY ($CAMPO_ID_CLIENTE) REFERENCES ${TabelaBDClientes.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, " +
        "FOREIGN KEY ($CAMPO_ID_VINHO) REFERENCES ${TabelaBDVinhos.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")

    }



    companion object{
        const val NOME = "vendas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_ID_CLIENTE = "clienteId"
        const val CAMPO_ID_VINHO = "vinhoId"
        const val CAMPO_NOME_CLIENTE = "nomeCliente"
        const val CAMPO_NOME_VINHO = "nomeVinho"
        const val CAMPO_QUANTIDADE = "quantidade"
        const val CAMPO_PRECO_GARRAFA = "precoGarrafa"
        const val CAMPO_PRECO = "preco"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_CLIENTE, CAMPO_NOME_VINHO, CAMPO_QUANTIDADE, CAMPO_PRECO_GARRAFA, CAMPO_PRECO, CAMPO_ID_CLIENTE, CAMPO_ID_VINHO)
    }
}