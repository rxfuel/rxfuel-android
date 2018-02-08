package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.FragmentActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.KClass

/**
 * Manages the interaction between view and ViewModel.
 *
 * @constructor Creates RxFuel instance with Context. Context must implement [RxFuelView]
 * @param context Context to create RxFuel instance.
 * @author Salah (nh.salah@gmail.com)
 */
class RxFuel(val context: FragmentActivity) {

    /**
     * Holds the main ViewModel observable and disposes on unbinding.
     */
    var disposables = CompositeDisposable()

    /**
     * Binds ViewModel to this activity. Invokes viewModel for processing events and subscription.
     *
     * @param viewModel to bind
     * @throws RxFuelException
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified E : RxFuelEvent, A : RxFuelAction, R : RxFuelResult, VS : RxFuelViewState, reified V : RxFuelView<E, VS>, VM :
    RxFuelViewModel<E, A, R, VS>> bind(viewModel: VM) {

        ViewModelFactory.instance.registerViewModel(viewModel)

        val rxFuelView : V

        if(context is V) {
            rxFuelView = context
        } else {
            throw RxFuelException("RxFuelView not implemented")
        }

        val persistedViewModel: VM = ViewModelProviders
                .of(context, ViewModelFactory.instance)
                .get(viewModel.javaClass)

        initialEventSubject
                .filter { it is E }
                .subscribe { persistedViewModel.initialEvent = it as E }

        disposables.add(persistedViewModel.states().subscribe({ viewState -> rxFuelView.render(viewState) }) { throw RxFuelException(it.message!!) })
        persistedViewModel.processEvents(if (rxFuelView.events() != null) rxFuelView.events() else null)

    }

    /**
     * Disposes states subscription
     */
    fun unbind() {
        disposables.dispose()
    }

    /**
     * Navigates to another Activity with an initial event
     *
     * @param dest destination activity class
     * @param initialEvent initial event to be triggered on destination activity
     */
    inline fun <reified E : RxFuelEvent> navigateTo(dest: KClass<out FragmentActivity>, initialEvent: E) {
        initialEventSubject.onNext(initialEvent)
        context.startActivity(Intent(context, dest.java))
    }

    /**
     * Navigates to another Activity
     *
     * @param dest destination activity class
     */
    fun navigateTo(dest: KClass<out FragmentActivity>) {
        context.startActivity(Intent(context, dest.java))
    }

    companion object {

        /**
         * Subject to handle Event passes between activities on navigation.
         */
        val initialEventSubject: BehaviorSubject<RxFuelEvent> = BehaviorSubject.create<RxFuelEvent>()
    }

}