package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVendas (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CAMPO_NOME_VINHO REFERENCES ${TabelaBDVinhos.CAMPO_NOME} ON DELETE RESTRICT," +
                "$CAMPO_NOME_CLIENTE REFERENCES ${TabelaBDClientes.CAMPO_NOME} ON DELETE RESTRICT,"+
        " $CAMPO_NUMERO INTEGER NOT NULL, $CAMPO_PRECO INTEGER NOT NULL)")
    }

    companion object{
        const val NOME = "vendas"
        const val CAMPO_NOME_CLIENTE = "nome_cliente"
        const val CAMPO_NOME_VINHO = "nome_vinho"
        const val CAMPO_NUMERO = "numero"
        const val CAMPO_PRECO = "preco"
    }
}