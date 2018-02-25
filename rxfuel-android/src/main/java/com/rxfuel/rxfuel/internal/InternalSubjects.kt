package com.rxfuel.rxfuel.internal

import com.rxfuel.rxfuel.RxFuelEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object InternalSubjects {

    val navigationAcknowledgment : PublishSubject<Unit> = PublishSubject.create()

    val initialEventSubject: BehaviorSubject<RxFuelEvent> = BehaviorSubject.create<RxFuelEvent>()
}