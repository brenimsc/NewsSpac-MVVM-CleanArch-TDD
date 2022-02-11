package com.android.newsspace.core

import kotlinx.coroutines.flow.Flow
import java.lang.UnsupportedOperationException


//use case generico
abstract class UseCase<Param, Source> {

    abstract suspend fun execute(param: Param): Flow<Source>

    //para quando chamar o objeto de getLatestNewUseCase, ou quem herde usecase, apenas chamando o construtor já estaremos chamando essa funcao do execute ou o nome que for
    suspend operator fun invoke(param: Param) = execute(param) // aqui estou referenciando a funcao abstract execute

    //classe de usacase, mas sem nenhum parametro, como se fosse um get simples na api
    abstract class NoParam<Source> : UseCase<Nothing, Source>() {

        abstract suspend fun execute(): Flow<Source>

        //sobrescita aqui e em mais lugar nenhum, para que la no getLatested nao peca para eu implementar essa classe ja que nao tem parametros
        final override suspend fun execute(param: Nothing): Flow<Source> {
            throw UnsupportedOperationException()
        }

        suspend operator fun invoke() = execute()


    }
}