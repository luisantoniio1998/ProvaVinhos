package com.example.provavinhos

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.provavinhos.BD.BDVinhosOpenHelper
import com.example.provavinhos.BD.TabelaBDVinhos
import com.example.provavinhos.BD.Wine

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




    @Before
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