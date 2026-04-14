package com.eapen.bookrecords.domain.transaction

import java.time.LocalDate

interface TransactionRepository {
    fun save(transaction: Transaction): Transaction

    fun findAllByDate(date: LocalDate): List<Transaction>

    fun findAllByMonthAndYear(
        month: Int,
        year: Int,
    ): List<Transaction>
}
