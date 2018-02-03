package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelProcessor
import com.rxfuel.rxfuelsample.network.GithubApi
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by salah on 2/2/18.
 */

class RepoListProcessor @Inject constructor() : RxFuelProcessor<RepoListAction, RepoListResult>() {

    @Inject
    lateinit var githubApi : GithubApi

    override val processors: HashMap<Class<out RepoListAction>, ObservableTransformer<out RepoListAction, out RepoListResult>>
        get() = hashMapOf(
                RepoListAction.SearchAction::class.java to processSearchRequest()
        )

    private fun processSearchRequest() =
            ObservableTransformer<RepoListAction.SearchAction, RepoListResult.SearchResult> { actions ->
                actions.flatMap { action ->
                    githubApi.getRepositories(action.query)
                            .subscribeOn(Schedulers.io())
                            .map{ response -> RepoListResult.SearchResult.Success(response.items) as RepoListResult.SearchResult }
                            .onErrorReturn { t -> RepoListResult.SearchResult.Failure(t.toString()) }
                            .observeOn(AndroidSchedulers.mainThread())
                            .startWith(RepoListResult.SearchResult.InFlight)
                }
            }

}