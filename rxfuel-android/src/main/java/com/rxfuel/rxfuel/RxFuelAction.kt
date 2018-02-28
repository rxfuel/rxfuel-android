package com.rxfuel.rxfuel

/**
 * Interface to implement Action. Action classes are holders of data that need to be executed by processors.
 *
 * @author Salah (nh.salah@gmail.com)
 */
interface RxFuelAction{

    companion object {

        /**
         *  An empty placeholder action to use when no action is required.
         */
        val NO_ACTION = object : RxFuelAction {}
    }
}