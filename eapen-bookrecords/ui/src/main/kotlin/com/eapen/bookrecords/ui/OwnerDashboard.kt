package com.eapen.bookrecords.ui

import kotlinx.html.*

fun HTML.ownerDashboard(
    ownerName: String,
    summaryTotal: Long,
) {
    baseLayout("Owner Dashboard - Eapen BookRecords") {
        div("flex justify-between items-center mb-8") {
            h1("text-2xl font-bold text-gray-900") { +"Business Dashboard" }
            div("flex items-center space-x-4") {
                span("text-sm text-gray-500") {
                    +"Logged in as "
                    b { +ownerName }
                }
                a(href = "/logout", classes = "text-sm text-business-700 hover:text-business-900") { +"Log out" }
            }
        }

        // Navigation / Tabs
        div("border-b border-gray-200 mb-8") {
            nav("-mb-px flex space-x-8") {
                a(
                    href = "/owner/dashboard",
                    classes = "border-business-500 text-business-600 whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm",
                ) {
                    +"Overview"
                }
                a(
                    href = "/employee/dashboard",
                    classes = "border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300 whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm",
                ) {
                    +"Daily Entry"
                }
            }
        }

        // Stats card
        div("grid grid-cols-1 md:grid-cols-3 gap-6 mb-8") {
            div("bg-white overflow-hidden shadow-sm rounded-lg border-t border-gray-100") {
                div("p-5") {
                    div("flex items-center") {
                        div("flex-shrink-0 bg-business-100 rounded-md p-3") {
                            // Simple icon
                            span("text-business-600 font-bold text-xl") { +"₹" }
                        }
                        div("ml-5 w-0 flex-1") {
                            dl {
                                dt("text-sm font-medium text-gray-500 truncate") { +"Total Income (This Month)" }
                                dd {
                                    div("text-lg font-semibold text-gray-900") { +"₹$summaryTotal" }
                                }
                            }
                        }
                    }
                }
            }

            // Further cards can be dynamically populated using HTMX here
        }

        // Transactions log
        div("bg-white shadow-sm overflow-hidden sm:rounded-md border border-gray-100") {
            div("px-4 py-5 sm:px-6 bg-gray-50 border-b border-gray-200") {
                h3("text-lg leading-6 font-medium text-gray-900") { +"Recent Ledger Log" }
                p("mt-1 max-w-2xl text-sm text-gray-500") { +"Combining both Books (Owner & Employee) entries." }
            }
            ul("divide-y divide-gray-200") {
                // Here we would iterate over recent transactions, for now placeholder
                li {
                    div("px-4 py-4 sm:px-6 hover:bg-gray-50") {
                        div("flex items-center justify-between") {
                            p("text-sm font-medium text-business-600 truncate") { +"Print/Scan/Copy" }
                            div("ml-2 flex-shrink-0 flex") {
                                p("px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800") {
                                    +"Completed"
                                }
                            }
                        }
                        div("mt-2 sm:flex sm:justify-between") {
                            div("sm:flex") {
                                p("flex items-center text-sm text-gray-500") {
                                    +"Recorded by Employee"
                                }
                            }
                            div("mt-2 flex items-center text-sm text-gray-500 sm:mt-0") {
                                +"Today"
                            }
                        }
                    }
                }
            }
        }
    }
}
