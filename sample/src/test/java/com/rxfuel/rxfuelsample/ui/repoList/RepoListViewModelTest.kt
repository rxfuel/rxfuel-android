package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.TestRxFuelViewModel
import com.rxfuel.rxfuelsample.data.api.ApiProcessorModule
import com.rxfuel.rxfuelsample.di.DaggerTestComponent
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import org.junit.After

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
        testRxFuel.events(RepoListEvent.Search("retrofit"))

        testObserver.assertValueAt(0, { state ->
            state == RepoListViewState(
                    false,
                    listOf(),
                    null,
                    null,
                    false,
                    null)
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


        testObserver.assertNoErrors()
    }

    @After
    fun after(){
        testRxFuel.destroy()
    }
}