package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.FragmentActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.KClass

/**
 * Created by salah on 29/1/18.
 */

class RxFuel(val context: FragmentActivity) {

    var disposables = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified E : RxFuelEvent,A : RxFuelAction,R : RxFuelResult, VS : RxFuelViewState,V : RxFuelView<E,VS>,VM :
    RxFuelViewModel<E,A,R,VS>> bind(viewModel : VM) {

        ViewModelFactory.instance.registerViewModel(viewModel)

        val rxFuelView : V = context as V
        val persistedViewModel : VM = getPersistedViewModel(viewModel)

        initialEventSubject
                .filter { it is E }
                .subscribe { persistedViewModel.initialEvent = it as E }

        disposables.add(persistedViewModel.states().subscribe ({viewState -> rxFuelView.render(viewState)}){ t -> throw t})
        persistedViewModel.processEvents(if(rxFuelView.events()!=null) rxFuelView.events() else null)

    }

    fun unbind(){
        disposables.dispose()
    }

    inline fun <reified E : RxFuelEvent> navigateTo(dest : KClass<out FragmentActivity>, initialEvent: E){
        initialEventSubject.onNext(initialEvent)
        context.startActivity(Intent(context,dest.java))
    }

    fun navigateTo(context: FragmentActivity,dest : KClass<out FragmentActivity>){
        context.startActivity(Intent(context,dest.java))
    }

    inline fun <reified E : RxFuelEvent,A : RxFuelAction,R : RxFuelResult, VS : RxFuelViewState,VM :
    RxFuelViewModel<E,A,R,VS>> getPersistedViewModel(viewModel: VM) : VM {
        return ViewModelProviders
                .of(context, ViewModelFactory.instance)
                .get(viewModel.javaClass)
    }

    companion object {
        val initialEventSubject : BehaviorSubject<RxFuelEvent> = BehaviorSubject.create<RxFuelEvent>()
    }

}