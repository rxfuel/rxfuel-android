package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.RxFuelResult
import com.rxfuel.rxfuelsample.model.Repo

/**
 * Created by salah on 2/2/18.
 */

sealed class DetailResult : RxFuelResult {
    data class DisplayRepoResult(val repo : Repo) : DetailResult()
}