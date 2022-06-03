package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVinhos (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, " +
                "$CAMPO_STOCK INTEGER NOT NULL, $CAMPO_ANO INTEGER NOT NULL," +
                "$CAMPO_PRECO DOUBLE NOT NULL," +
              "$CAMPO_REGION TEXT NOT NULL)")
    }

    companion object{
        const val NOME = "vinhos"
        const val CAMPO_NOME = "nome_vinho"
        const val CAMPO_STOCK = "stock"
        const val CAMPO_REGION = "region"
        const val CAMPO_ANO = "safra"
        const val CAMPO_PRECO = "preco"
    }
}