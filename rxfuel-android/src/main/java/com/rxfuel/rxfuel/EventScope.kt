package com.rxfuel.rxfuel

/**
 * Annotate Events to specify scope.
 * Events with [Scope.DOMAIN] will be mapped to action to trigger processor.
 * Events with [Scope.UI] will be used to generate ViewState.
 *
 * @author Salah (nh.salah@gmail.com)
 */
@Target(AnnotationTarget.CLASS)
annotation class EventScope(val scope : Scope)