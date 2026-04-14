package com.eapen.bookrecords.domain.user

data class User(
    val id: Long? = null,
    val username: String,
    val role: Role,
)
