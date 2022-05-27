package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDClientes (db:SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$CAMPO_NOME VARCHAR NOT NULL ,$CAMPO_CONTACTO INT(9) NOT NULL, " +
                "$CAMPO_NIF INT(9) NOT NULL," +
                " $CAMPO_CODIGO_POSTAL VARCHAR NOT NULL)")
    }

    companion object{
            const val NOME = "clientes"
            const val CAMPO_NOME = "nome"
            const val CAMPO_CONTACTO = "contacto"
            const val CAMPO_NIF = "NIF"
            const val CAMPO_CODIGO_POSTAL = "codigo_postal"
        }
    }