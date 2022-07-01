package com.example.provavinhos.BD

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDVinhos (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$CAMPO_NOME_VINHO TEXT NOT NULL, " +
        "$CAMPO_PRECO_GARRAFA REAL NOT NULL, " +
        "$CAMPO_STOCK INTEGER NOT NULL, " +
        "$CAMPO_ID_REGIAO INTEGER NOT NULL, " +
        "FOREIGN KEY ($CAMPO_ID_REGIAO) REFERENCES ${TabelaBDRegiao.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
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
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDRegiao.NOME} ON ${TabelaBDRegiao.CAMPO_ID} = $CAMPO_ID_REGIAO"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }
    companion object{
        const val NOME = "vinhos"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_ID_REGIAO = "Idregiao"
        const val CAMPO_NOME_VINHO = "nomeVinho"
        const val CAMPO_PRECO_GARRAFA = "precoGarrafa"
        const val CAMPO_STOCK = "stock"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_VINHO, CAMPO_PRECO_GARRAFA, CAMPO_STOCK, CAMPO_ID_REGIAO, TabelaBDRegiao.CAMPO_NOME)
    }
}