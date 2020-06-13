package com.example.pokepedia.models

class NetworkState(val status: Status, val msg: String) {

    companion object {
        var LOADED: NetworkState? = null
        var LOADING: NetworkState? = null

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
        }
    }

    enum class Status {
        RUNNING, SUCCESS, FAILED
    }

}