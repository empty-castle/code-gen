package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.panel.ObservableProperties

object CodeGen {

    // todo Entity 생성
    fun genEntity(observableProperties: ObservableProperties): String {

        return """
            package com.github.wesbin.codegen.dialog

            import com.github.wesbin.codegen.dialog.panel.ObservableProperties

            object CodeGen {

                // todo Entity 생성
                fun genEntity(observableProperties: ObservableProperties): String {

                    return ""
                }
            }
        """.trimIndent()
    }
}