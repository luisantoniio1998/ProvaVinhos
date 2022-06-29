package com.example.provavinhos.BD

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderVendas : ContentProvider() {
    var dbOpenHelper : BDVinhosOpenHelper? = null
    override fun onCreate(): Boolean {
        dbOpenHelper = BDVinhosOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper!!.readableDatabase

        requireNotNull(projection)
        val colunas = projection as Array<String>

        val argsSeleccao = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        val cursor = when (getUriMatcher().match(uri)) {
            URI_VENDAS -> TabelaBDVendas(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_VENDA_ESPECIFICA -> TabelaBDVendas(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_CLIENTES -> TabelaBDClientes(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_CLIENTE_ESPECIFICO -> TabelaBDClientes(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_REGIOES -> TabelaBDRegiao(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_REGIAO_ESPECIFICA -> TabelaBDRegiao(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_VINHOS -> TabelaBDVinhos(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_VINHO_ESPECIFICO -> TabelaBDVinhos(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)

            else -> null
        }

        return cursor
    }

    override fun getType(uri: Uri): String? =
        when (getUriMatcher().match(uri)) {
            URI_VENDAS -> "$MULTIPLOS_REGISTOS/${TabelaBDVendas.NOME}"
            URI_VENDA_ESPECIFICA -> "$UNICO_REGISTO/${TabelaBDVendas.NOME}"
            URI_CLIENTES -> "$MULTIPLOS_REGISTOS/${TabelaBDClientes.NOME}"
            URI_CLIENTE_ESPECIFICO-> "$UNICO_REGISTO/${TabelaBDClientes.NOME}"
            URI_REGIOES-> "$MULTIPLOS_REGISTOS/${TabelaBDRegiao.NOME}"
            URI_REGIAO_ESPECIFICA-> "$UNICO_REGISTO/${TabelaBDRegiao.NOME}"
            URI_VINHOS -> "$MULTIPLOS_REGISTOS/${TabelaBDVendas.NOME}"
            URI_VINHO_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDVendas.NOME}"
            else -> null
        }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_VENDAS -> TabelaBDVendas(db).insert(values)
            URI_CLIENTES -> TabelaBDClientes(db).insert(values)
            URI_REGIOES -> TabelaBDRegiao(db).insert(values)
            URI_VINHOS -> TabelaBDVinhos(db).insert(values)
            else -> -1
        }

        db.close()

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, "$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosApagados = when (getUriMatcher().match(uri)) {
            URI_VENDA_ESPECIFICA -> TabelaBDVendas(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_CLIENTE_ESPECIFICO -> TabelaBDClientes(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_VINHO_ESPECIFICO -> TabelaBDVinhos(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_REGIAO_ESPECIFICA-> TabelaBDRegiao(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosApagados
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        requireNotNull(values)

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosAlterados = when (getUriMatcher().match(uri)) {
            URI_CLIENTE_ESPECIFICO -> TabelaBDClientes(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_VENDA_ESPECIFICA -> TabelaBDVendas(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_REGIAO_ESPECIFICA -> TabelaBDRegiao(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_VINHO_ESPECIFICO -> TabelaBDVinhos(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosAlterados
    }

    companion object{
        private const val AUTHORITY = "com.example.provavinhos"

        private const val URI_VENDAS = 100
        private const val URI_VENDA_ESPECIFICA = 101
        private const val URI_CLIENTES = 200
        private const val URI_CLIENTE_ESPECIFICO = 201
        private const val URI_VINHOS = 300
        private const val URI_VINHO_ESPECIFICO = 301
        private const val URI_REGIOES = 400
        private const val URI_REGIAO_ESPECIFICA = 401


        private const val UNICO_REGISTO = "vnd.android.cursor.item"
        private const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        val ENDERECO_VENDAS = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDVendas.NOME)
        val ENDERECO_CLIENTES =  Uri.withAppendedPath(ENDERECO_BASE, TabelaBDClientes.NOME)
        val ENDERECO_VINHOS = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDVinhos.NOME)
        val ENDERECO_REGIOES = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDRegiao.NOME)

        fun getUriMatcher(): UriMatcher {
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaBDVendas.NOME, URI_VENDAS)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDVendas.NOME}/#", URI_VENDA_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, TabelaBDClientes.NOME, URI_CLIENTES )
            uriMatcher.addURI(AUTHORITY, "${TabelaBDClientes.NOME}/#", URI_CLIENTE_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, TabelaBDVinhos.NOME, URI_VINHOS )
            uriMatcher.addURI(AUTHORITY, "${TabelaBDVinhos.NOME}/#", URI_VINHO_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, TabelaBDRegiao.NOME, URI_REGIOES )
            uriMatcher.addURI(AUTHORITY, "${TabelaBDRegiao.NOME}/#", URI_REGIAO_ESPECIFICA)


            return uriMatcher
        }

    }
}