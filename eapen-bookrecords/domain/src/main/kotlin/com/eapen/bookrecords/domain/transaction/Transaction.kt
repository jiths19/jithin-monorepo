package com.eapen.bookrecords.domain.transaction

import com.eapen.bookrecords.domain.category.Category
import com.eapen.bookrecords.domain.user.User
import java.time.LocalDate

data class Transaction(
    val id: Long? = null,
    val date: LocalDate,
    val amount: Long,
    val description: String? = null,
    val recordedBy: User,
    val category: Category,
)
