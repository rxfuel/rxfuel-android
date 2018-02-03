package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelResult
import com.rxfuel.rxfuelsample.model.Repo

/**
 * Created by salah on 2/2/18.
 */

sealed class RepoListResult : RxFuelResult {

    object InitialResult : RepoListResult()

    sealed class SearchResult : RepoListResult() {
        data class Success(val repos : List<Repo>) : SearchResult()
        object InFlight : SearchResult()
        data class Failure(val errorMessage : String) : SearchResult()
    }

}