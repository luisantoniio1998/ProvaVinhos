package com.example.provavinhos.BD

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDClientes (db:SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$CAMPO_NOME TEXT NOT NULL ,$CAMPO_CONTACTO TEXT NOT NULL, " +
                "$CAMPO_NIF TEXT NOT NULL)")
    }


    companion object{
            const val NOME = "clientes"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
            const val CAMPO_NOME = "nomeCliente"
            const val CAMPO_CONTACTO = "contacto"
            const val CAMPO_NIF = "NIF"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_CONTACTO, CAMPO_NIF)
        }
    }