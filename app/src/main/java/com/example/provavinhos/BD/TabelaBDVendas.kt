package com.example.provavinhos.BD

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDVendas (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$CAMPO_QUANTIDADE INTEGER NOT NULL, " +
        "$CAMPO_PRECO REAL NOT NULL, " +
        "$CAMPO_ID_CLIENTE INTEGER NOT NULL, " +
        "$CAMPO_ID_VINHO INTEGER NOT NULL," +
        "FOREIGN KEY ($CAMPO_ID_CLIENTE) REFERENCES ${TabelaBDClientes.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, " +
        "FOREIGN KEY ($CAMPO_ID_VINHO) REFERENCES ${TabelaBDVinhos.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")

    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDClientes.NOME} ON ${TabelaBDClientes.CAMPO_ID} = $CAMPO_ID_CLIENTE" +
                " INNER JOIN ${TabelaBDVinhos.NOME} ON ${TabelaBDVinhos.CAMPO_ID} = $CAMPO_ID_VINHO" +


        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    companion object{
        const val NOME = "vendas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_ID_CLIENTE = "clienteId"
        const val CAMPO_ID_VINHO = "vinhoId"
        const val CAMPO_QUANTIDADE = "quantidade"
        const val CAMPO_PRECO = "preco"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID,CAMPO_QUANTIDADE, CAMPO_PRECO,  CAMPO_ID_CLIENTE, CAMPO_ID_VINHO, TabelaBDClientes.CAMPO_NOME,
            TabelaBDClientes.CAMPO_CONTACTO, TabelaBDClientes.CAMPO_NIF,  TabelaBDVinhos.CAMPO_NOME_VINHO,
        TabelaBDVinhos.CAMPO_ID_REGIAO, TabelaBDVinhos.CAMPO_STOCK, TabelaBDRegiao.CAMPO_NOME)
    }
}