package com.rxfuel.rxfuel

/**
 * Interface to implement Event.
 * Events should hold data of all user interactions and Android provided data such as location updates, gyro etc
 *
 * @author Salah (nh.salah@gmail.com)
 */
interface RxFuelEvent{

    /**
     * Implementation should override property [isLocal].
     * Set [isLocal] to true if the event doesn't require async processing.
     * Process and map local events directly to result in [RxFuelViewModel.eventToResult]
     * If [isLocal] is set to false, create corresponding action and handle process in processor class.
     */
    val isLocal : Boolean
}