package com.example.provavinhos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.provavinhos.BD.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDVinhosOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereVinho(db: SQLiteDatabase, vinho : Wine) {
        vinho.id = TabelaBDVinhos(db).insert(vinho.toContentValues())
        assertNotEquals(-1, vinho.id)
    }

    private fun insereCliente(db: SQLiteDatabase, client : Clients) {
        client.id = TabelaBDClientes(db).insert(client.toContentValues())
        assertNotEquals(-1, client.id)
    }

    private fun insereVenda(db: SQLiteDatabase, venda : Sales) {
        venda.id = TabelaBDVendas(db).insert(venda.toContentValues())
        assertNotEquals(-1, venda.id)
    }

    private fun insereRegiao(db: SQLiteDatabase, regiao : Region) {
        regiao.id = TabelaBDRegiao(db).insert(regiao.toContentValues())
        assertNotEquals(-1, regiao.id)
    }



    @Test
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDVinhosOpenHelper.NOME)
    }

    @Test
    fun  consegueAbrirBaseDados() {
        val openHelper = BDVinhosOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

}