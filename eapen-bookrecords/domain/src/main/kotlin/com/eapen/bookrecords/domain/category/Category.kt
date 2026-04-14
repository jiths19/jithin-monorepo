package com.eapen.bookrecords.domain.category

data class Category(
    val id: Long? = null,
    val name: String,
    val isSystem: Boolean = false,
)
