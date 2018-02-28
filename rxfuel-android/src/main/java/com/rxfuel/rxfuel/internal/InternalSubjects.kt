package com.rxfuel.rxfuel.internal

import com.rxfuel.rxfuel.RxFuelEvent
import io.reactivex.subjects.BehaviorSubject

object InternalSubjects {
    val initialEventSubject: BehaviorSubject<RxFuelEvent> = BehaviorSubject.create<RxFuelEvent>()
}