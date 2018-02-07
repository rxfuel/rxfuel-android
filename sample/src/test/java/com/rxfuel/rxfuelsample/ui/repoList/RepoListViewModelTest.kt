package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuelsample.di.DaggerTestComponent
import io.reactivex.observers.TestObserver
import org.junit.Before
import com.rxfuel.rxfuelsample.network.GithubApi
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Test
import javax.inject.Inject
import io.reactivex.schedulers.Schedulers
import org.junit.After

/**
 * Created by salah on 3/2/18.
 */

class RepoListViewModelTest {

    lateinit var repoListViewModel: DetailViewModel

    lateinit var testObserver: TestObserver<RepoListViewState>

    @Inject
    lateinit var githubApi: GithubApi

    @Before
    fun setupRepoListViewModelTest() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler {Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        val component = DaggerTestComponent.builder().build()

        component.inject(this)

        repoListViewModel = DetailViewModel(RepoListProcessor(githubApi))

        testObserver = repoListViewModel.states().test()

    }

    @Test
    fun testRepoList() {
        repoListViewModel.processEvents(Observable.just(
                DetailEvent.Search("retrofit")
        ))

        testObserver.assertValueAt(2,
                { state ->
                    state.repos.size == 2 &&
                    state.repos[0].full_name == "mRepo1" &&
                    state.repos[1].full_name == "mRepo2"
                }
        )

        testObserver.assertNoErrors()
    }

    @After
    fun After(){
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}