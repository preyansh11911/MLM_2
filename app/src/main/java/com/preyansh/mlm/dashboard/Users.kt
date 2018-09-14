package com.preyansh.mlm.dashboard

import com.support.POJOModel

data class Users(var name: String = "", var number: String = "") : POJOModel() {
    init {
        Users.id++
        this.id=Users.id
        name+=this.id
    }
    companion object {
        var id: Long = 0
    }
}