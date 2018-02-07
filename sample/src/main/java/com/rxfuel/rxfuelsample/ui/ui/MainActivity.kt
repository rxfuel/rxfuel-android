package com.rxfuel.rxfuelsample.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding2.widget.textChanges
import com.rxfuel.rxfuel.RxFuelView
import com.rxfuel.rxfuelsample.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), RxFuelView<MainEvent, MainViewState> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun localEvents(): Observable<MainEvent>? {
        return null
    }

    override fun events(): Observable<MainEvent>? {
        return et_query.textChanges().debounce(3,TimeUnit.SECONDS).map { query -> MainEvent.Search(query.toString()) }
    }

    override fun render(state: MainViewState) {
        progressBar.visibility = if(state.inFlight) VISIBLE else GONE
        tv_temp.visibility = if(state.inFlight) GONE else VISIBLE
        if(!state.inFlight){
            tv_temp.text = state.repos.toString()
        }
    }

}
