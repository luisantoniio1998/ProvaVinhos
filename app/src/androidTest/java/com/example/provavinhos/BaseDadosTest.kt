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

    private fun insereCliente(db: SQLiteDatabase, clients: Clients) {
        clients.id = TabelaBDClientes(db).insert(clients.toContentValues())
        assertNotEquals(-1, clients.id)
    }

    private fun insereVenda(db: SQLiteDatabase, sales: Sales) {
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

        val region1 = Region("Douro")
        insereRegiao(db, region1)

        val region2 = Region("Minho")
        insereRegiao(db, region2)

        val vinho = Wine("Monte da Peceguina", 13.50,10, region)
        vinho.id = TabelaBDVinhos(db).insert(vinho.toContentValues())

        val vinho2 = Wine("Papa Figos", 12.5,  100, region)
        vinho2.id = TabelaBDVinhos(db).insert(vinho2.toContentValues())

        val vinho3 = Wine("Vinho Verde", 15.5,  10, region)
        vinho3.id = TabelaBDVinhos(db).insert(vinho3.toContentValues())


        assertNotEquals(-1, vinho.id)
        assertNotEquals(-1, vinho2.id)
        assertNotEquals(-1, vinho3.id)

        db.close()
    }

    @Test

    fun consegueInserirCliente() {
        val db = getWritableDatabase()

        val client = Clients("Luis Barros", "913131311", "133313231")
        val client2 = Clients("Joao Antonio", "93413413", "231413131")
        client.id = TabelaBDClientes(db).insert(client.toContentValues())
        client2.id = TabelaBDClientes(db).insert(client2.toContentValues())

        assertNotEquals(-1, client.id)
        assertNotEquals(-1, client2.id)
        db.close()
    }

    @Test

    fun consegueInserirVenda() {
        val db = getWritableDatabase()

        val client = Clients("Luis Barros", "931123123", "123321132")
        insereCliente(db, client)

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val vinho = Wine("Monte da Peceguina", 13.50, 10, regiao)
        insereVinho(db, vinho)

        val venda =
            Sales(  10, (vinho.precoGarrafa * 10), client, vinho, regiao)
        venda.id = TabelaBDVendas(db).insert(venda.toContentValues())

        assertNotEquals(-1, venda.id)

        db.close()

    }

    @Test

    fun consegueAlterarRegiao() {
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        regiao.nomeRegiao = "Douro"

        val registosAlterados = TabelaBDRegiao(db).update(
            regiao.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${regiao.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarVinho() {
        val db = getWritableDatabase()

        val regiaoAlentejo = Region("Aletenjo")
        insereRegiao(db, regiaoAlentejo)

        val regiaoDouro = Region("Douro")
        insereRegiao(db, regiaoDouro)

        val vinho =
            Wine("Encosta do Guadiana", 21.0, 5, regiaoAlentejo)
        insereVinho(db, vinho)

        vinho.nomeVinho = "Ervideira"
        vinho.precoGarrafa = 12.27
        vinho.stock = 4
        vinho.regiao = regiaoDouro

        val registosAlterados = TabelaBDVinhos(db).update(
            vinho.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${vinho.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test

    fun consegueAlterarCliente() {
        val db = getWritableDatabase()

        val client = Clients("Rui ", "936593434", "231431413")
        insereCliente(db, client)

        client.nome = "Joao Barros"
        client.contacto = "93432113"
        client.nif = "23143131"

        val registosAlterados = TabelaBDClientes(db).update(
            client.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${client.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()
    }


    @Test

    fun consegueAlterarVenda() {
        val db = getWritableDatabase()

        val regiaoAlentejo = Region("Alentejo")
        insereRegiao(db, regiaoAlentejo)

        val regiaoDouro = Region("Douro")
        insereRegiao(db, regiaoDouro)

        val clienteLuis = Clients("Luis", "936593777", "231481933")
        insereCliente(db, clienteLuis)

        val clienteJoao = Clients("Joao", "933455676", "231411311")
        insereCliente(db, clienteJoao)

        val vinhoAlentejo =
            Wine("Dona Maria", 8.90,  10, regiaoAlentejo)
        insereVinho(db, vinhoAlentejo)

        val vinhoDouro = Wine("Arca Nova", 8.08,  5, regiaoDouro)
        insereVinho(db, vinhoDouro)

        val venda = Sales(
            10,
            vinhoDouro.precoGarrafa * 10,
            clienteJoao,
            vinhoDouro,
            regiaoDouro
        )
        insereVenda(db, venda)

        venda.cliente = clienteLuis
        venda.vinho= vinhoAlentejo
        venda.region = regiaoDouro
        venda.quantidade = 5
        venda.preco = (5 * vinhoAlentejo.precoGarrafa)

        val registosAlterados = TabelaBDVendas(db).update(
            venda.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${venda.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test

    fun consegueEliminarRegiao() {
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val registosEliminados = TabelaBDRegiao(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${regiao.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarVinho() {
        val db = getWritableDatabase()

        val region = Region("Alentejo")
        insereRegiao(db, region)

        val vinho = Wine("Monte da Peceguina", 13.50, 10, region)
        insereVinho(db, vinho)

        val registosEliminados = TabelaBDVinhos(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${vinho.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test

    fun consegueEliminarCliente() {
        val db = getWritableDatabase()

        val cliente = Clients("Luis", "936543123", "231413213")
        insereCliente(db, cliente)

        val registosEliminados = TabelaBDClientes(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${cliente.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueEliminarVenda() {
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val cliente = Clients("Luis", "936593777", "231481933")
        insereCliente(db, cliente)

        val vinho = Wine("Dona Maria", 8.90,  10, regiao)
        insereVinho(db, vinho)

        val venda =
            Sales( 10, vinho.precoGarrafa * 10, cliente, vinho, regiao)
        insereVenda(db, venda)


        val registosEliminados = TabelaBDVendas(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${venda.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()

    }

    @Test
    fun consegueLerRegioes() {
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val cursor = TabelaBDRegiao(db).query(
            TabelaBDRegiao.TODAS_COLUNAS,
            "${TabelaBDRegiao.CAMPO_ID}=?",
            arrayOf("${regiao.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val regBD = Region.fromCursor(cursor)

        assertEquals(regiao, regBD)
    }

    @Test
    fun consegueLerClientes() {
        val db = getWritableDatabase()

        val cliente = Clients("Vasco", "936593766", "231481934")
        insereCliente(db, cliente)

        val cursor = TabelaBDClientes(db).query(
            TabelaBDClientes.TODAS_COLUNAS,
            "${TabelaBDClientes.CAMPO_ID}=?",
            arrayOf("${cliente.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val clientBD = Clients.fromCursor(cursor)

        assertEquals(cliente, clientBD)
    }

    @Test
    fun consegueLerVinhos() {
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val vinho = Wine("Alucinado", 10.3,  10, regiao)
        insereVinho(db, vinho)

        val cursor = TabelaBDVinhos(db).query(
            TabelaBDVinhos.TODAS_COLUNAS,
            "${TabelaBDVinhos.CAMPO_ID}=?",
            arrayOf("${vinho.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val vinhoBD = Wine.fromCursor(cursor)

        assertEquals(vinho, vinhoBD)
    }

    @Test
    fun consegueLerVendas() {
        val db = getWritableDatabase()

        val regiao = Region("Alentejo")
        insereRegiao(db, regiao)

        val vinho = Wine("Alucinado", 10.3, 10, regiao)
        insereVinho(db, vinho)

        val cliente = Clients("Luis", "936593777", "231481933")
        insereCliente(db, cliente)

        val venda = Sales( 10, 103.0,cliente, vinho, regiao)
        insereVenda(db, venda)

        val cursor = TabelaBDVendas(db).query(
            TabelaBDVendas.TODAS_COLUNAS,
            "${TabelaBDVendas.CAMPO_ID}=?",
            arrayOf("${venda.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val vendaBD = Sales.fromCursor(cursor)

        assertEquals(venda, vendaBD)
    }



}