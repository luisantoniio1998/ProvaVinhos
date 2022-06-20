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

    private fun insereRegiao(db: SQLiteDatabase, region: Region) {
        region.id = TabelaBDRegiao(db).insert(region.toContentValues())
        assertNotEquals(-1, region.id)
    }

    private fun insereVinho(db: SQLiteDatabase, wine: Wine) {
        wine.id = TabelaBDVinhos(db).insert(wine.toContentValues())
        assertNotEquals(-1, wine.id)
    }

    private fun insereCliente(db: SQLiteDatabase, clients: Clients){
        clients.id = TabelaBDClientes(db).insert(clients.toContentValues())
        assertNotEquals(-1, clients.id)
    }

    private fun insereVenda(db:SQLiteDatabase, sales: Sales){
        sales.id = TabelaBDVendas(db).insert(sales.toContentValues())
        assertNotEquals(-1, sales.id)
    }


    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDVinhosOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDVinhosOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test

    fun consegueInserirRegiao() {
        val db = getWritableDatabase()

        insereRegiao(db, Region("Alentejo"))

        db.close()
    }

    @Test
    fun consegueInserirVinho() {
        val db = getWritableDatabase()

        val region = Region("Alentejo")
        insereRegiao(db, region)

        val vinho = Wine("Monte da Peceguina", 13.50, region.nomeRegiao, 10, region.id)
        vinho.id = TabelaBDVinhos(db).insert(vinho.toContentValues())

        assertNotEquals(-1, vinho.id)

        db.close()
    }

    @Test

    fun consegueInserirCliente(){
        val db = getWritableDatabase()

        val client = Clients("Luis Barros", "913131311", "133313231")
        client.id = TabelaBDClientes(db).insert(client.toContentValues())

        assertNotEquals(-1, client.id)

        db.close()
    }

    @Test

    fun consegueInserirVenda(){
        val db = getWritableDatabase()

        val client = Clients("Luis Barros", "931123123", "123321132")
        insereCliente(db, client)

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val vinho = Wine("Monte da Peceguina", 13.50, regiao.nomeRegiao, 10, regiao.id)
        insereVinho(db, vinho)

        val venda = Sales(client.nome, vinho.nomeVinho, 10, vinho.precoGarrafa, 135.0, client.id, vinho.id)
        venda.id = TabelaBDVendas(db).insert(venda.toContentValues())

        assertNotEquals(-1, venda.id)

        db.close()

    }

    @Test

    fun consegueAlterarRegiao(){
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        regiao.nomeRegiao= "Douro"

        val registosAlterados = TabelaBDRegiao(db).update(
            regiao.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${regiao.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

}