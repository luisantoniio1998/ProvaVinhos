package com.example.provavinhos.BD

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class ContentProviderRegioes : ContentProvider() {

    var db : BDVinhosOpenHelper? = null
    override fun onCreate(): Boolean {
        db = BDVinhosOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object{
        const val AUTHORITY = "com.example.provavinhos"

        const val URI_REGIOES = 100
        const val URI_REGIAO_ESPECIFICA = 101

        fun getUriMatcher(): UriMatcher{
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaBDRegiao.NOME, URI_REGIOES)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDRegiao.NOME}/#", URI_REGIAO_ESPECIFICA)


            return uriMatcher
        }

    }
}