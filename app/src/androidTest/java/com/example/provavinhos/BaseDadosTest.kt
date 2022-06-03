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

    @Test

    fun consegueInserirVinho() {
        val db = getWritableDatabase()

        insereVinho(db, Wine("Dez tostoes", 15, 2017, 12.8, "Alentejo"))

        db.close()
    }

    @Test
    fun consegueInserirCliente(){
        val db = getWritableDatabase()

        insereCliente(db, Clients("Luis Barros", 917773333, 231441321, "6270-276"))

        db.close()
    }

    @Test

    fun consegueInserirRegiao(){
        val db = getWritableDatabase()

        insereRegiao(db, Region("Alentejo"))

        db.close()
    }

    @Test

    fun consegueInserirVenda(){
        val db = getWritableDatabase()

        insereVenda(db, Sales("Luis Barros", "Dez tostoes", 12.8, 10, 128.0  ))

        db.close()
    }

    @Test
    fun consegueAlterarVinhos() {

       val db = getWritableDatabase()

        val vinho = Wine("Casal Garcia", 12, 1998, 12.0, "Alenetejo")
        insereVinho(db, vinho)

        vinho.nome = "Dez tostoes"
        vinho.stock = 10
        vinho.ano = 2017
        vinho.regiao = "Douro"
        vinho.preco = 12.0

        val registosAlterados = TabelaBDVinhos(db).update(
            vinho.toContentValues(),
        "${BaseColumns._ID}= ?",
        arrayOf("${vinho.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarClientes() {


    }

    @Test
    fun consegueAlterarRegioes() {

        //TODO
    }

    @Test
    fun consegueAlterarVendas() {

        //TODO
    }



}