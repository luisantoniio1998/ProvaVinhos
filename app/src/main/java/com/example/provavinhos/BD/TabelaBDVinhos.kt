package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVinhos (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$CAMPO_NOME_VINHO TEXT NOT NULL, " +
        "$CAMPO_PRECO_GARRAFA REAL NOT NULL, " +
        "$CAMPO_NOME_REGIAO TEXT NOT NULL, " +
        "$CAMPO_STOCK INTEGER NOT NULL, " +
        "$CAMPO_ID_REGIAO INTEGER NOT NULL, " +
        "FOREIGN KEY ($CAMPO_ID_REGIAO) REFERENCES ${TabelaBDRegiao.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME = "vinhos"
        const val CAMPO_ID_REGIAO = "regiaoId"
        const val CAMPO_NOME_VINHO = "nomeVinho"
        const val CAMPO_PRECO_GARRAFA = "precoGarrafa"
        const val CAMPO_NOME_REGIAO = "nomeRegiao"
        const val CAMPO_STOCK = "stock"
    }
}