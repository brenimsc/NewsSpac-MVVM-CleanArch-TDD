package com.android.newsspace.test

import com.android.newsspace.configureTestAppComponent
import com.android.newsspace.data.NewsCategory
import com.android.newsspace.data.model.News
import com.android.newsspace.domain.GetLatesteNewsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetLatesteNewsUseCaseTest : KoinTest {

    val getLatesteNewsUseCase: GetLatesteNewsUseCase by inject()


    //isso é para iniciar as dependencia que sao injetadas pelo Koin
    companion object {

        //realizado apeans uma vez
        @BeforeClass
        @JvmStatic // para que a maquina saiba que é um este static
        fun setup() {
            configureTestAppComponent()
        }

        //para desligar a aplicacao do koin
        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }


    @Test
    fun deve_RetornarResultadoNaoNulo_AoConectar_Com_Repositorio() {
        runBlocking { //para esperarmos o resultado e nao entrar na corrotina
            val result = getLatesteNewsUseCase(NewsCategory.ARTICLE.value)

            assertNotNull(result)
        }
    }

    @Test
    fun deve_RetornarResultadoDoTipoCorreto() {
        runBlocking {
            val result = getLatesteNewsUseCase(NewsCategory.ARTICLE.value)

            assertTrue(result is Flow<List<News>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio() {
        runBlocking {
            val result = getLatesteNewsUseCase(NewsCategory.ARTICLE.value)

            assertFalse(result.first().isEmpty())
        }
    }




}