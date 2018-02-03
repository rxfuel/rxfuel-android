package com.rxfuel.rxfuelsample.network

import com.rxfuel.rxfuelsample.model.RepositoriesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by salah on 2/2/18.
 */

interface GithubApi {

    @GET("/search/repositories")
    fun getRepositories(@Query("q") q : String): Observable<RepositoriesResponse>

}