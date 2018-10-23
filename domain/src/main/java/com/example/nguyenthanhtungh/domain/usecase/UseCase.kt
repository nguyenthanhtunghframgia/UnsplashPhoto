package com.example.nguyenthanhtungh.domain.usecase

abstract class UseCase<in Param, out T> where T : Any {

    abstract fun createObservable(param: Param? = null): T

    abstract fun onCleared()
}
