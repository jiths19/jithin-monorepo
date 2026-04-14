package com.eapen.bookrecords.infrastructure.db

import com.eapen.bookrecords.domain.transaction.Transaction
import com.eapen.bookrecords.domain.transaction.TransactionRepository
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class ExposedTransactionRepository : TransactionRepository {
    override fun save(tx: Transaction): Transaction {
        return transaction {
            // Find or create User
            val userEnt =
                UserEntity.find { UsersTable.username eq tx.recordedBy.username }.firstOrNull()
                    ?: UserEntity.new {
                        username = tx.recordedBy.username
                        role = tx.recordedBy.role.name
                    }

            // Find or create Category
            val categoryEnt =
                CategoryEntity.find { CategoriesTable.name eq tx.category.name }.firstOrNull()
                    ?: CategoryEntity.new {
                        name = tx.category.name
                        isSystem = tx.category.isSystem
                    }

            val txId = tx.id
            val saved =
                if (txId != null) {
                    TransactionEntity[txId].apply {
                        date = tx.date
                        amount = tx.amount
                        description = tx.description
                        recordedBy = userEnt
                        category = categoryEnt
                    }
                } else {
                    TransactionEntity.new {
                        date = tx.date
                        amount = tx.amount
                        description = tx.description
                        recordedBy = userEnt
                        category = categoryEnt
                    }
                }

            saved.toDomain()
        }
    }

    override fun findAllByDate(date: LocalDate): List<Transaction> {
        return transaction {
            TransactionEntity.find { TransactionsTable.date eq date }
                .map { it.toDomain() }
        }
    }

    override fun findAllByMonthAndYear(
        month: Int,
        year: Int,
    ): List<Transaction> {
        val startDate = LocalDate.of(year, month, 1)
        val endDate = startDate.plusMonths(1).minusDays(1)

        return transaction {
            TransactionEntity.find {
                (TransactionsTable.date greaterEq startDate) and
                    (TransactionsTable.date lessEq endDate)
            }.map { it.toDomain() }
        }
    }
}
