package com.example.provavinhos.BD

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderRegioes : ContentProvider() {
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
            URI_REGIOES -> TabelaBDRegiao(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_REGIAO_ESPECIFICA -> TabelaBDRegiao(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            else -> null
        }

        db.close()

        return cursor
    }

    override fun getType(uri: Uri): String? =
        when (getUriMatcher().match(uri)) {
            URI_REGIOES -> "$MULTIPLOS_REGISTOS/${TabelaBDRegiao.NOME}"
            URI_REGIAO_ESPECIFICA -> "$UNICO_REGISTO/${TabelaBDRegiao.NOME}"
            else -> null
        }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_REGIOES -> TabelaBDRegiao(db).insert(values)
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
            URI_REGIAO_ESPECIFICA -> TabelaBDRegiao(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
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
            URI_REGIAO_ESPECIFICA -> TabelaBDRegiao(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))

            else -> 0
        }

        db.close()

        return registosAlterados
    }

    companion object{
        const val AUTHORITY = "com.example.provavinhos"

        const val URI_REGIOES = 100
        const val URI_REGIAO_ESPECIFICA = 101

        const val UNICO_REGISTO = "vnd.android.cursor.item"
        const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        fun getUriMatcher(): UriMatcher{
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaBDRegiao.NOME, URI_REGIOES)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDRegiao.NOME}/#", URI_REGIAO_ESPECIFICA)


            return uriMatcher
        }

    }
}