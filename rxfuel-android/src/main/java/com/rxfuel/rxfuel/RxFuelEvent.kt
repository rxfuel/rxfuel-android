package com.rxfuel.rxfuel

/**
 * Interface to implement Event. Implementation should override property [isLocal].
 * Set [isLocal] to true if the event doesn't require async processing.
 * Process and map local events directly to result in [RxFuelViewModel.eventToResult]
 * If [isLocal] is set to false, create corresponding action and handle process in processor class.
 *
 * @author Salah (nh.salah@gmail.com)
 */
interface RxFuelEvent{
    val isLocal : Boolean
}