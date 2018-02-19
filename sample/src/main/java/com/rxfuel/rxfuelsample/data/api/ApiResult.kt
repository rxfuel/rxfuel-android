package com.rxfuel.rxfuelsample.data.api

import com.rxfuel.rxfuel.RxFuelResult
import com.rxfuel.rxfuelsample.model.Repo

/**
 * Created by salah on 2/2/18.
 */

sealed class ApiResult : RxFuelResult {

    object InitialResult : ApiResult()

    sealed class SearchResult : ApiResult() {
        data class Success(val repos : List<Repo>) : SearchResult()
        object InFlight : SearchResult()
        data class Failure(val errorMessage : String) : SearchResult()
    }

    data class RepoClickResult(val repo : Repo) : ApiResult()
}