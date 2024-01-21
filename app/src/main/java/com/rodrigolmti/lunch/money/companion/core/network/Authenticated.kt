package com.rodrigolmti.lunch.money.companion.core.network

/**
 * Annotation used to mark endpoints that require authentication.
 * This annotation is used by
 * [com.rodrigolmti.lunch.money.companion.core.network.AuthInterceptor]
 * to add the Authorization header to the request.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated