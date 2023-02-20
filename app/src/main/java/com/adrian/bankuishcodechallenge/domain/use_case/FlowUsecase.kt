package com.adrian.bankuishcodechallenge.domain.use_case

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
abstract class FlowUsecase<Params, Response> {

    private val _trigger = MutableStateFlow(true)

    /**
     * Exposes result of this use case
     */
    val resultFlow: Flow<Response> = _trigger.flatMapLatest {
        execute()
    }
    /**
     * Triggers the execution of this use case
     */
    suspend fun launch() {
        _trigger.emit(!(_trigger.value))
    }

    abstract suspend fun execute(params: Params? = null) : Flow<Response>
}