package com.rxfuel.rxfuel

import io.reactivex.Observable

/**
 * Created by salah on 24/1/18.
 */

interface RxFuelView<E : RxFuelEvent, in VS : RxFuelViewState> {
    fun events(): Observable<E>?
    fun render(state: VS)
}