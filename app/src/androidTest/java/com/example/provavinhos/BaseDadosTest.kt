package com.example.provavinhos

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.provavinhos.BD.BDVinhosOpenHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

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