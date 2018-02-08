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

    val rxFuel : RxFuel = RxFuel(this)

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        rxFuel.bind(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxFuel.unbind()
    }
    override fun events(): Observable<DetailEvent>? = null

    override fun render(state: DetailViewState) {
        Log.d("DetailActivity",state.toString())
        this.supportActionBar?.title = state.repo?.full_name
        tv_description.text = state.repo?.description
        tv_owner_name.text = state.repo?.owner?.login
    }

}
