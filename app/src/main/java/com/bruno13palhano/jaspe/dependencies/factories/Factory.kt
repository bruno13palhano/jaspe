package com.bruno13palhano.jaspe.dependencies.factories

interface Factory<T> {
    fun create(): T
}