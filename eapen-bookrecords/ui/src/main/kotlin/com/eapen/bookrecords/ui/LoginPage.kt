package com.eapen.bookrecords.ui

import kotlinx.html.*

fun HTML.loginPage() {
    baseLayout("Eapen BookRecords - Login") {
        div("flex min-h-full flex-col justify-center py-12 sm:px-6 lg:px-8") {
            div("sm:mx-auto sm:w-full sm:max-w-md") {
                h2("mt-6 text-center text-3xl font-bold tracking-tight text-business-900") {
                    +"Eapen BookRecords"
                }
                p("mt-2 text-center text-sm text-gray-600") {
                    +"Sign in to log daily entries."
                }
            }

            div("mt-8 sm:mx-auto sm:w-full sm:max-w-md") {
                div("bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10 border-t-4 border-business-500") {
                    form(action = "/login", method = FormMethod.post, classes = "space-y-6") {
                        div {
                            label(classes = "block text-sm font-medium text-gray-700") { +"Username" }
                            div("mt-1") {
                                input(
                                    type = InputType.text,
                                    name = "username",
                                    classes = "block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-business-500 focus:outline-none focus:ring-business-500 sm:text-sm",
                                ) {
                                    required = true
                                    placeholder = "owner or employee"
                                }
                            }
                        }

                        div {
                            label(classes = "block text-sm font-medium text-gray-700") { +"Password" }
                            div("mt-1") {
                                input(
                                    type = InputType.password,
                                    name = "password",
                                    classes = "block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-business-500 focus:outline-none focus:ring-business-500 sm:text-sm",
                                ) {
                                    required = true
                                    placeholder = "••••••••"
                                }
                            }
                        }

                        div {
                            button(
                                type = ButtonType.submit,
                                classes = "flex w-full justify-center rounded-md border border-transparent bg-business-700 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-business-900 focus:outline-none focus:ring-2 focus:ring-business-500 focus:ring-offset-2 transition-colors duration-200",
                            ) {
                                +"Sign in"
                            }
                        }
                    }
                }
            }
        }
    }
}
