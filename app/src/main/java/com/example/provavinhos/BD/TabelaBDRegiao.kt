package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDRegiao (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CAMPO_NOME REFERENCES ${TabelaBDVinhos.CAMPO_REGION} ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME = "regiao"
        const val CAMPO_NOME = "nome_regiao"
    }
}