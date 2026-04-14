package com.eapen.bookrecords.ui

import kotlinx.html.*
import java.time.LocalDate

fun HTML.employeeDashboard(
    currentDate: LocalDate,
    employeeName: String,
) {
    baseLayout("Dashboard - Eapen BookRecords") {
        div("flex justify-between items-center mb-8") {
            h1("text-2xl font-bold text-gray-900") { +"Daily Ledger" }
            div("flex items-center space-x-4") {
                span("text-sm text-gray-500") {
                    +"Logged in as "
                    b { +employeeName }
                }
                a(href = "/logout", classes = "text-sm text-business-700 hover:text-business-900") { +"Log out" }
            }
        }

        div("grid grid-cols-1 lg:grid-cols-3 gap-8") {
            // Entry Form
            div("lg:col-span-1 bg-white rounded-xl shadow-sm border border-gray-100 p-6") {
                h2("text-lg font-medium text-gray-900 mb-6 border-b pb-2") { +"New Entry" }

                // HTMX Form pointing to /api/transaction
                form(action = "/api/transaction", method = FormMethod.post, classes = "space-y-4") {
                    attributes["hx-post"] = "/api/transaction"
                    attributes["hx-target"] = "#transaction-list" // updating the table
                    attributes["hx-swap"] = "afterbegin"
                    attributes["hx-on::after-request"] = "if(event.detail.successful) this.reset()"

                    div {
                        label(classes = "block text-sm font-medium text-gray-700") { +"Type / Category" }
                        div("mt-1") {
                            input(
                                type = InputType.text,
                                name = "category",
                                classes = "block w-full rounded-md border-gray-300 shadow-sm focus:border-business-500 focus:ring-business-500 sm:text-sm border py-2 px-3",
                            ) {
                                required = true
                                placeholder = "e.g., Print, Scan, Recharge"
                                attributes["list"] = "categories" // Datalist for autosuggest
                            }
                        }
                    }

                    div {
                        label(classes = "block text-sm font-medium text-gray-700") { +"Amount" }
                        div("mt-1 relative rounded-md shadow-sm") {
                            div("pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3") {
                                span("text-gray-500 sm:text-sm") { +"₹" } // Rs currency symbol
                            }
                            input(
                                type = InputType.number,
                                name = "amount",
                                classes = "block w-full rounded-md border-gray-300 pl-7 focus:border-business-500 focus:ring-business-500 sm:text-sm border py-2",
                            ) {
                                required = true
                                min = "1"
                                placeholder = "0.00"
                            }
                        }
                    }

                    div {
                        label(classes = "block text-sm font-medium text-gray-700") { +"Notes (Optional)" }
                        div("mt-1") {
                            input(
                                type = InputType.text,
                                name = "description",
                                classes = "block w-full rounded-md border-gray-300 shadow-sm focus:border-business-500 focus:ring-business-500 sm:text-sm border py-2 px-3",
                            ) {
                                placeholder = "Client name or detail"
                            }
                        }
                    }

                    button(
                        type = ButtonType.submit,
                        classes = "mt-4 w-full rounded-md border border-transparent bg-business-700 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-business-900 focus:outline-none focus:ring-2 focus:ring-business-500 focus:ring-offset-2",
                    ) {
                        +"Add Record"
                    }
                }

                // Datalist holding dynamic categories
                unsafe {
                    raw(
                        """
                        <datalist id="categories">
                            <option value="Print/Scan/Copy"></option>
                            <option value="CSC"></option>
                            <option value="Recharge"></option>
                        </datalist>
                        """.trimIndent(),
                    )
                }
            }

            // Today's Entries
            div("lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden") {
                div("p-6 border-b border-gray-100 flex justify-between items-center bg-gray-50") {
                    h2("text-lg font-medium text-gray-900") { +"Today's Transactions" }
                    span("px-3 py-1 bg-business-100 text-business-900 rounded-full text-xs font-semibold tracking-wide uppercase") {
                        +currentDate.toString()
                    }
                }

                div("overflow-x-auto") {
                    table("min-w-full divide-y divide-gray-200") {
                        thead("bg-gray-50") {
                            tr {
                                th(
                                    classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider",
                                ) { +"Category" }
                                th(classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider") { +"Notes" }
                                th(
                                    classes = "px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider",
                                ) { +"Amount" }
                            }
                        }
                        tbody("bg-white divide-y divide-gray-200") {
                            id = "transaction-list"
                            // HTMX will inject elements here. Initially empty or loaded from server.
                        }
                    }
                }
            }
        }
    }
}

// A partial HTML template just for a row, so HTMX can swap it in
fun TBODY.transactionRow(
    category: String,
    description: String,
    amount: Long,
) {
    tr("hover:bg-gray-50 transition-colors duration-150") {
        td("px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900") { +category }
        td("px-6 py-4 whitespace-nowrap text-sm text-gray-500") { +(description.ifBlank { "-" }) }
        td("px-6 py-4 whitespace-nowrap text-sm text-gray-900 font-semibold text-right") { +"₹$amount" }
    }
}
