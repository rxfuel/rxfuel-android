package com.rxfuel.rxfuelsample.data.api

import com.rxfuel.rxfuel.RxFuelProcessor
import com.rxfuel.rxfuel.RxFuelProcessorModule
import com.rxfuel.rxfuelsample.network.GithubApi
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiProcessorModule @Inject constructor(): RxFuelProcessorModule {

    @Inject
    lateinit var githubApi : GithubApi

    @RxFuelProcessor(ApiAction.SearchAction::class)
    val SearchRequestProcessor =
            ObservableTransformer<ApiAction.SearchAction, ApiResult.SearchResult> { actions ->
                actions.flatMap { action ->
                    githubApi.getRepositories(action.query)
                            .subscribeOn(Schedulers.io())
                            .map { response -> ApiResult.SearchResult.Success(response.items) as ApiResult.SearchResult}
                            .onErrorReturn { t -> ApiResult.SearchResult.Failure(t.toString())}
                            .observeOn(AndroidSchedulers.mainThread())
                            .startWith(ApiResult.SearchResult.InFlight)
                }
            }
}