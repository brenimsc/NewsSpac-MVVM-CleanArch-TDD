package com.android.newsspace.data

import com.android.newsspace.data.service.NewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertEquals


//indicando que vai ser um runner de teste JUnit4
@RunWith(JUnit4::class)
class NewsServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var service: NewsService

    //executada antes de cada teste
    @Before
    fun createService() {

        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(NewsService::class.java)

    }


    //desligar o servidor depois de testar
    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun deve_EncontrarOEndpointCorreto_AoReceberParamento() {
        runBlocking {

            //testando o caminho dos endpoints

            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val resultArticles = service.getNewsApi(NewsCategory.ARTICLE.value)
            val requestArticles = mockWebServer.takeRequest()
            assertEquals(requestArticles.path, "/articles")

            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val resultBlog = service.getNewsApi(NewsCategory.BLOGS.value)
            val requestBlog = mockWebServer.takeRequest()
            assertEquals(requestBlog.path, "/blogs")

            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val resultReports = service.getNewsApi(NewsCategory.REPORTS.value)
            val requestReports = mockWebServer.takeRequest()
            assertEquals(requestReports.path, "/reports")

        }

    }


}