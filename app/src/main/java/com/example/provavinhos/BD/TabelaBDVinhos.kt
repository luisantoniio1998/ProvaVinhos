package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVinhos (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME VARCHAR NOT NULL " +
                "$CAMPO_STOCK INT(3) NOT NULL, $CAMPO_ANO INT(4) NOT NULL," +
                " FOREIGN KEY ($CAMPO_REGIAO) REFERENCES ${TabelaBDRegiao.CAMPO_NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME = "clientes"
        const val CAMPO_NOME = "nome"
        const val CAMPO_STOCK = "stock"
        const val CAMPO_REGIAO = "regiao"
        const val CAMPO_ANO = "safra"
    }
}