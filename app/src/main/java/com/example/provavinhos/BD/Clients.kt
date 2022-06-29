package com.example.provavinhos.BD

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Clients(
    var nome: String,
    var contacto: String,
    var nif: String,
    var id: Long =-1
):Serializable {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDClientes.CAMPO_NOME, nome)
        valores.put(TabelaBDClientes.CAMPO_CONTACTO, contacto)
        valores.put(TabelaBDClientes.CAMPO_NIF, nif)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Clients {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NOME)
            val posContacto = cursor.getColumnIndex(TabelaBDClientes.CAMPO_CONTACTO)
            val posNif = cursor.getColumnIndex(TabelaBDClientes.CAMPO_NIF)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val contacto = cursor.getString(posContacto)
            val nif = cursor.getString(posNif)

            return Clients(nome, contacto, nif, id)
        }
    }
}