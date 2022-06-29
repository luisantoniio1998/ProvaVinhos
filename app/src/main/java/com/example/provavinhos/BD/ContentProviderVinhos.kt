package com.example.provavinhos.BD

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteOpenHelper

class ContentProviderVinhos : ContentProvider(){
    var db : BDVinhosOpenHelper? = null
    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     *
     * You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via [.query], [.insert], etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     *
     * If you use SQLite, [android.database.sqlite.SQLiteOpenHelper]
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * [android.database.sqlite.SQLiteOpenHelper.getReadableDatabase] or
     * [android.database.sqlite.SQLiteOpenHelper.getWritableDatabase]
     * from this method.  (Instead, override
     * [android.database.sqlite.SQLiteOpenHelper.onOpen] to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
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

    override fun getType(uri: Uri): String? =
        when(getUriMatcher().match(uri)){
            URI_VINHOS -> "$MULTIPLOS_REGISTOS/${TabelaBDVinhos.NOME}"
            URI_REGIOES -> "$MULTIPLOS_REGISTOS/${TabelaBDRegiao.NOME}"
            URI_VINHO_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDVinhos.NOME}"
            URI_REGIAO_ESPECIFICA -> "$UNICO_REGISTO/${TabelaBDRegiao.NOME}"
            else -> null
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
        const val URI_VINHOS = 200
        const val URI_VINHO_ESPECIFICO = 201

        const val UNICO_REGISTO = "vnd.android.cursor.item"
        const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        fun getUriMatcher() : UriMatcher{

            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaBDRegiao.NOME, URI_REGIOES)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDRegiao.NOME}/#", URI_REGIAO_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, TabelaBDVinhos.NOME, URI_VINHOS)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDVinhos.NOME}/#", URI_VINHO_ESPECIFICO)

            return uriMatcher
        }
    }
}