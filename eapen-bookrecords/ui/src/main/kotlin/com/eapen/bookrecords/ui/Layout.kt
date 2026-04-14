package com.eapen.bookrecords.ui

import kotlinx.html.*

fun HTML.baseLayout(
    titleText: String,
    block: MAIN.() -> Unit,
) {
    head {
        title { +titleText }
        // Tailwind CSS (CDN for simple local deployment)
        script { src = "https://cdn.tailwindcss.com" }
        // Tailwind Config for custom class configurations
        script {
            unsafe {
                raw(
                    """
                    tailwind.config = {
                        theme: {
                            extend: {
                                colors: {
                                    business: {
                                        50: '#f0fdfa',
                                        100: '#ccfbf1',
                                        500: '#14b8a6', // Teal
                                        700: '#0f766e',
                                        900: '#134e4a', // Dark classy mode base
                                        950: '#042f2e'
                                    }
                                }
                            }
                        }
                    }
                """,
                )
            }
        }
        // HTMX (CDN for simple dynamic views)
        script { src = "https://unpkg.com/htmx.org@1.9.10" }

        style {
            unsafe {
                raw("body { font-family: 'Inter', sans-serif; background-color: #f3f4f6; }")
            }
        }
    }
    body("bg-gray-50 text-gray-900 antialiased min-h-screen") {
        div("w-full h-2 bg-business-700") {} // Top colored branding strip

        main("max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8", block)
    }
}
