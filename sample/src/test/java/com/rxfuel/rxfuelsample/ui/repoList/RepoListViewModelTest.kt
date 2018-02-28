package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.TestRxFuelViewModel
import com.rxfuel.rxfuelsample.data.api.ApiProcessorModule
import com.rxfuel.rxfuelsample.di.DaggerTestComponent
import com.rxfuel.rxfuelsample.model.Repo
import com.rxfuel.rxfuelsample.ui.detail.DetailActivity
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import org.junit.After
import org.mockito.Mockito.mock

class RepoListViewModelTest {

    private lateinit var testRxFuel: TestRxFuelViewModel<RepoListEvent, RepoListViewState>

    private lateinit var testObserver: TestObserver<RepoListViewState>

    @Inject
    lateinit var apiProcessorModule : ApiProcessorModule

    @Before
    fun setupRepoListViewModelTest() {
        DaggerTestComponent.builder().build().inject(this)
        testRxFuel = TestRxFuelViewModel(RepoListViewModel())
                .withProcessorModules(apiProcessorModule)
                .synchronously()

        testObserver = testRxFuel.getObserver()
    }

    @Test
    fun testRepoList() {
        testRxFuel.events(
                RepoListEvent.Search("retrofit"),
                RepoListEvent.RepoClick(mock(Repo::class.java))
        )

        System.out.println(testObserver.values().toString())

        testObserver.assertValueAt(0, { state ->
            state == RepoListViewState.idle()
        })

        testObserver.assertValueAt(1, { state ->
            state == RepoListViewState(
                    true,
                    listOf(),
                    null,
                    null,
                    true,
                    null)
        })

        testObserver.assertValueAt(2, { state ->
            state.repos.size == 2 &&
                    state.repos[0].full_name == "mRepo1" &&
                    state.repos[1].full_name == "mRepo2"
        })

        testObserver.assertValueAt(3, { state ->
            state.navigate == DetailActivity::class
        })

        //test if state after navigation is observed
        testObserver.assertValueAt(4, { state ->
            state.navigate == null
        })

        testObserver.assertNoErrors()
    }

    @After
    fun after(){
        testRxFuel.destroy()
    }
}