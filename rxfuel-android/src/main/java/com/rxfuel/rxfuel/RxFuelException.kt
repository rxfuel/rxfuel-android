package com.rxfuel.rxfuel

/**
 * Custom exception class to handle errors.
 *
 * @param message Error message to throw.
 * @author Salah (nh.salah@gmail.com)
 */
class RxFuelException(override var message:String): Exception(message)