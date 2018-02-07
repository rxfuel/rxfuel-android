package com.rxfuel.rxfuelsample.ui.detail

import android.os.Bundle
import android.util.Log
import com.rxfuel.rxfuel.RxFuel
import com.rxfuel.rxfuel.RxFuelView
import com.rxfuel.rxfuelsample.R
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_repo_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), RxFuelView<DetailEvent,DetailViewState> {

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        RxFuel.bind(this,viewModel)
    }

    override fun events(): Observable<DetailEvent>? = null

    override fun render(state: DetailViewState) {
        Log.d("DetailActivity",state.toString())
        tv_repo_name.text = state.repo?.full_name
    }

}
