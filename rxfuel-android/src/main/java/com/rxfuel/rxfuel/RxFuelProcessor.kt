package com.rxfuel.rxfuel

import kotlin.reflect.KClass

/**
 * Annotate processors with action class. Processor will run when the specified action is invoked.
 *
 * @author Salah (nh.salah@gmail.com)
 */
@Target(AnnotationTarget.FIELD)
annotation class RxFuelProcessor(val actionClass: KClass<out RxFuelAction>)