package com.example.provavinhos.BD

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDVendas (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PRIMARY FOREIGN KEY ($CAMPO_NOME_VINHO) REFERENCES ${TabelaBDClientes.CAMPO_NOME}(${BaseColumns._ID}) ON DELETE RESTRICT," +
                "PRIMARY FOREIGN KEY ($CAMPO_NOME_CLIENTE) REFERENCES ${TabelaBDClientes.CAMPO_NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)"+
        " $CAMPO_NUMERO INT(9) NOT NULL, $CAMPO_PRECO INT(5,2))")
    }

    companion object{
        const val NOME = "clientes"
        const val CAMPO_NOME_CLIENTE = "nome_cliente"
        const val CAMPO_NOME_VINHO = "nome_vinho"
        const val CAMPO_NUMERO = "numero"
        const val CAMPO_PRECO = "preco"
    }
}