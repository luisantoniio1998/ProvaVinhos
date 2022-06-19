package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVendas (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$CAMPO_ID_CLIENTE INTEGER NOT NULL, " +
        "FOREIGN KEY ($CAMPO_ID_CLIENTE) REFERENCES ${TabelaBDClientes.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, " +
        "$CAMPO_ID_VINHO INTEGER NOT NULL, " +
        "FOREIGN KEY ($CAMPO_ID_VINHO) REFERENCES ${TabelaBDVinhos.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, " +
        "$CAMPO_NOME_CLIENTE TEXT NOT NULL, " +
        "$CAMPO_NOME_VINHO TEXT NOT NULL, " +
        "$CAMPO_QUANTIDADE INTEGER NOT NULL, " +
        "$CAMPO_PRECO_GARRAFA REAL NOT NULL, "  +
        "$CAMPO_PRECO REAL NOT NULL)")

    }


    companion object{
        const val NOME = "vendas"
        const val CAMPO_ID_CLIENTE = "clienteId"
        const val CAMPO_ID_VINHO = "vinhoId"
        const val CAMPO_NOME_CLIENTE = "nomeCliente"
        const val CAMPO_NOME_VINHO = "vinhoCliente"
        const val CAMPO_QUANTIDADE = "quantidade"
        const val CAMPO_PRECO_GARRAFA = "precoGarrafa"
        const val CAMPO_PRECO = "preco"
    }
}