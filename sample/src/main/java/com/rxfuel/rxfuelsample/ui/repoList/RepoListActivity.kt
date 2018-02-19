package com.rxfuel.rxfuelsample.ui.repoList

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.jakewharton.rxbinding2.widget.textChanges
import com.rxfuel.rxfuel.RxFuel
import com.rxfuel.rxfuel.RxFuelView
import com.rxfuel.rxfuelsample.R
import com.rxfuel.rxfuelsample.model.Repo
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.view.inputmethod.InputMethodManager
import com.rxfuel.rxfuelsample.ui.detail.DetailActivity
import com.rxfuel.rxfuelsample.ui.detail.DetailEvent

class RepoListActivity : DaggerAppCompatActivity(), RxFuelView<RepoListEvent, RepoListViewState> {

    private val rxFuel : RxFuel = RxFuel(this)

    @Inject
    lateinit var repoListViewModel : RepoListViewModel

    private val reposSubject: PublishSubject<List<Repo>> = PublishSubject.create()

    private val reposAdapter = RepoListAdapter(reposSubject)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        rv_repos.adapter = reposAdapter
        rxFuel.bind(repoListViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxFuel.unbind()
    }

    override fun events(): Observable<RepoListEvent>? {
        return Observable.merge(
                et_query.textChanges()
                        .debounce(1,TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .skip(1)
                        .filter { it.length > 3 }
                        .map { query -> RepoListEvent.Search(query.toString()) },
                reposAdapter.itemClicks()
                        .map { repo -> RepoListEvent.RepoClick(repo) }
                )
    }

    override fun render(state: RepoListViewState) {
        progressBar.visibility = if(state.loading) VISIBLE else GONE
        rv_repos.visibility = if(state.loading || state.repos.isEmpty()) GONE else VISIBLE
        if(!state.loading)
            reposSubject.onNext(state.repos)
        if(state.hideKeyboard)
            hideKeyboard()
        if(state.errorMessage!=null)
            Toast.makeText(this,state.errorMessage,LENGTH_LONG).show()
        if(state.navigate!=null) {
            when(state.navigate){
                RepoListActivity::class -> rxFuel.navigateTo(
                        DetailActivity::class,
                        DetailEvent.DisplayRepoEvent(state.lastClickedRepo!!)
                )
            }
        }
    }

    private fun hideKeyboard() {
        if (et_query != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(et_query.windowToken, 0)
        }
    }
}
