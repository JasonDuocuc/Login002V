package com.example.login002v.data.model

data class Credential(
    val username: String,
    val password: String
) {
    companion object {
        val Admin = Credential(username = "huerto", password = "hogar123")
    }
}
