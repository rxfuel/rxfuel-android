package com.rxfuel.rxfuelsample.di

import com.rxfuel.rxfuelsample.model.Repo
import com.rxfuel.rxfuelsample.model.RepositoriesResponse
import com.rxfuel.rxfuelsample.network.GithubApi
import io.reactivex.Observable
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.mock.BehaviorDelegate

/**
 * Created by salah on 2/2/18.
 */

class FakeGithubApi(delegate : BehaviorDelegate<GithubApi>) : GithubApi{

    var mdelegate: BehaviorDelegate<GithubApi> = delegate

    override fun getRepositories(q: String): Observable<RepositoriesResponse> {

        val mockedRepositoriesResponse = mock(RepositoriesResponse::class.java)

        val mockedRepoList = listOf(
                mock(Repo::class.java),
                mock(Repo::class.java)
        )
        `when`(mockedRepoList[0].full_name).thenReturn("mRepo1")
        `when`(mockedRepoList[1].full_name).thenReturn("mRepo2")
        `when`(mockedRepositoriesResponse.items).thenReturn(mockedRepoList)
        return mdelegate.returningResponse(mockedRepositoriesResponse).getRepositories(q)
    }

}