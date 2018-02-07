package com.rxfuel.rxfuelsample.ui.repoList

import android.os.Bundle
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

class RepoListActivity : DaggerAppCompatActivity(), RxFuelView<RepoListEvent, RepoListViewState> {

    @Inject
    lateinit var repoListViewModel : RepoListViewModel

    private val reposSubject: PublishSubject<List<Repo>> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_repos.adapter = RepoListAdapter(reposSubject)

        RxFuel.bind(this,repoListViewModel)
    }

    override fun events(): Observable<RepoListEvent>? {
        return et_query.textChanges()
                .filter { it.length > 3 }
                .debounce(1,TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map { query -> RepoListEvent.Search(query.toString()) }
    }

    override fun render(state: RepoListViewState) {
        progressBar.visibility = if(state.loading) VISIBLE else GONE
        rv_repos.visibility = if(state.loading || state.repos.isEmpty()) GONE else VISIBLE
        if(!state.loading) reposSubject.onNext(state.repos)
        if(state.errorMessage!=null) Toast.makeText(this,state.errorMessage,LENGTH_LONG).show()
    }

}
