package com.eapen.bookrecords.infrastructure.db

import com.eapen.bookrecords.domain.user.Role
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

object UsersTable : LongIdTable("users") {
    val username = varchar("username", 255).uniqueIndex()
    val role = varchar("role", 50)
}

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UsersTable)

    var username by UsersTable.username
    var role by UsersTable.role

    fun toDomain() =
        com.eapen.bookrecords.domain.user.User(
            id = id.value,
            username = username,
            role = Role.valueOf(role),
        )
}

object CategoriesTable : LongIdTable("categories") {
    val name = varchar("name", 255).uniqueIndex()
    val isSystem = bool("is_system").default(false)
}

class CategoryEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CategoryEntity>(CategoriesTable)

    var name by CategoriesTable.name
    var isSystem by CategoriesTable.isSystem

    fun toDomain() =
        com.eapen.bookrecords.domain.category.Category(
            id = id.value,
            name = name,
            isSystem = isSystem,
        )
}

object TransactionsTable : LongIdTable("transactions") {
    val date = date("tx_date")
    val amount = long("amount")
    val description = varchar("description", 500).nullable()
    val recordedByUserId = reference("recorded_by_user_id", UsersTable)
    val categoryId = reference("category_id", CategoriesTable)
}

class TransactionEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TransactionEntity>(TransactionsTable)

    var date by TransactionsTable.date
    var amount by TransactionsTable.amount
    var description by TransactionsTable.description
    var recordedBy by UserEntity referencedOn TransactionsTable.recordedByUserId
    var category by CategoryEntity referencedOn TransactionsTable.categoryId

    fun toDomain() =
        com.eapen.bookrecords.domain.transaction.Transaction(
            id = id.value,
            date = date,
            amount = amount,
            description = description,
            recordedBy = recordedBy.toDomain(),
            category = category.toDomain(),
        )
}
