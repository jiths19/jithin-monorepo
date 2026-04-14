package com.eapen.bookrecords.application

import com.eapen.bookrecords.domain.category.Category
import com.eapen.bookrecords.domain.transaction.Transaction
import com.eapen.bookrecords.domain.user.Role
import com.eapen.bookrecords.domain.user.User
import com.eapen.bookrecords.infrastructure.db.CategoriesTable
import com.eapen.bookrecords.infrastructure.db.ExposedTransactionRepository
import com.eapen.bookrecords.infrastructure.db.TransactionsTable
import com.eapen.bookrecords.infrastructure.db.UsersTable
import com.eapen.bookrecords.ui.employeeDashboard
import com.eapen.bookrecords.ui.loginPage
import com.eapen.bookrecords.ui.ownerDashboard
import com.eapen.bookrecords.ui.transactionRow
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.html.stream.appendHTML
import kotlinx.html.tbody
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.time.LocalDate

fun main() {
    val dbFile = File("bookrecords.db")
    Database.connect("jdbc:sqlite:${dbFile.absolutePath}", driver = "org.sqlite.JDBC")

    transaction {
        SchemaUtils.create(UsersTable, CategoriesTable, TransactionsTable)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val repository = ExposedTransactionRepository()

    routing {
        get("/") {
            call.respondHtml {
                loginPage()
            }
        }

        get("/employee/dashboard") {
            // Placeholder: Fetch all transactions for today
            val todayTx = repository.findAllByDate(LocalDate.now())

            call.respondHtml {
                employeeDashboard(LocalDate.now(), "Employee #1")
            }
        }

        get("/owner/dashboard") {
            // Find transactions for this month
            val now = LocalDate.now()
            val monthTx = repository.findAllByMonthAndYear(now.monthValue, now.year)
            val summaryTotal = monthTx.sumOf { it.amount }

            call.respondHtml {
                ownerDashboard("Shop Owner", summaryTotal)
            }
        }

        post("/api/transaction") {
            val params = call.receiveParameters()
            val categoryName = params["category"] ?: "Unknown"
            val amount = params["amount"]?.toLongOrNull() ?: 0L
            val description = params["description"]?.takeIf { it.isNotBlank() }

            // Hardcode Employee User for now
            val employee = User(username = "Employee #1", role = Role.EMPLOYEE)
            val category = Category(name = categoryName)

            val tx =
                Transaction(
                    date = LocalDate.now(),
                    amount = amount,
                    description = description,
                    recordedBy = employee,
                    category = category,
                )

            repository.save(tx)

            // Return HTMX partial row
            val snippet =
                buildString {
                    appendHTML().tbody {
                        transactionRow(categoryName, description ?: "", amount)
                    }
                }
            // In Ktor, HTMX injects the inner HTML by default or depending on OOB properties.
            // Since we targeted `#transaction-list` which is a TBODY, we can just respond with the TR.
            // Wait, Ktor appendHTML().tr{} will create just the row! Let's do TR inside stream.
            // Actually kotlinx.html `appendHTML().tr` requires `flowContent`, `tbody` just makes it safer.
            // Let's rewrite snippet to just TR string because appendHTML doesn't let root be TR without a custom tag consumer trick.
            val rawHtml =
                """
                <tr class="hover:bg-gray-50 transition-colors duration-150">
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">$categoryName</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${description?.ifBlank { "-" } ?: "-"}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900 font-semibold text-right">₹$amount</td>
                </tr>
                """.trimIndent()

            call.respondText(rawHtml, io.ktor.http.ContentType.Text.Html)
        }
    }
}
