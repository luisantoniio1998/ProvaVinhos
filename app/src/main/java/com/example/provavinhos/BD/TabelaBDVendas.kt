package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVendas (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FOREIGN KEY $CAMPO_NOME_VINHO REFERENCES ${TabelaBDVinhos.CAMPO_NOME}(${BaseColumns._ID}) ON DELETE RESTRICT," +
                "FOREIGN KEY $CAMPO_NOME_CLIENTE REFERENCES ${TabelaBDClientes.CAMPO_NOME}(${BaseColumns._ID}) ON DELETE RESTRICT," +
                "FOREIGN KEY $CAMPO_PRECO_GARRAFA REFERENCES ${TabelaBDVinhos.CAMPO_PRECO}(${BaseColumns._ID}) ON DELETE RESTRICT,"+
        "$CAMPO_NUMERO INTEGER NOT NULL, $CAMPO_PRECO DOUBLE NOT NULL)")
    }

    companion object{
        const val NOME = "vendas"
        const val CAMPO_NOME_CLIENTE = "nome_cliente"
        const val CAMPO_NOME_VINHO = "nome_vinho"
        const val CAMPO_NUMERO = "numero"
        const val CAMPO_PRECO_GARRAFA = "preco_garrafa"
        const val CAMPO_PRECO = "preco"
    }
}