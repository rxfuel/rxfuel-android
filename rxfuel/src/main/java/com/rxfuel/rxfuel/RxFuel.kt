package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.KClass

/**
 * Created by salah on 29/1/18.
 */

object RxFuel {

    val initialStateSubject : BehaviorSubject<RxFuelEvent> = BehaviorSubject.create<RxFuelEvent>()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified E : RxFuelEvent,A : RxFuelAction,R : RxFuelResult, VS : RxFuelViewState,V : RxFuelView<E,VS>,VM :
    RxFuelViewModel<E,A,R,VS>> bind(context: FragmentActivity,newViewModel : VM){

        ViewModelFactory.instance.registerViewModel(newViewModel)

        val rxFuelView : V = context as V
        val viewModel : VM = getPersistedViewModel(context,newViewModel)

        initialStateSubject.take(1).filter { it is E }.subscribe {
                viewModel.initialEvent = it as E
        }

        viewModel.states().subscribe ({viewState -> rxFuelView.render(viewState)}){ t -> throw t}
        viewModel.processEvents(
                if(rxFuelView.events()!=null) rxFuelView.events() else null,
                if(rxFuelView.localEvents()!=null) rxFuelView.localEvents() else null
        )

    }

    inline fun <reified E : RxFuelEvent> navigateTo(context: FragmentActivity,dest : KClass<out FragmentActivity>, initialEvent: E){
        initialStateSubject.onNext(initialEvent)
        context.startActivity(Intent(context,dest.java))
    }

    inline fun <reified E : RxFuelEvent,A : RxFuelAction,R : RxFuelResult, VS : RxFuelViewState,VM :
    RxFuelViewModel<E,A,R,VS>> getPersistedViewModel(context: FragmentActivity, viewModel: VM) : VM {
        return ViewModelProviders
                .of(context, ViewModelFactory.instance)
                .get(viewModel.javaClass)
    }

}