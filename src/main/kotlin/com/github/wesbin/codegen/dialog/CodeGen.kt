package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.panel.ObservableProperties

object CodeGen {

    // todo Entity 생성
    fun genEntity(observableProperties: ObservableProperties): String {

        return "package com.github.wesbin.codegen.dialog\n" +
                "\n" +
                "import com.github.wesbin.codegen.dialog.panel.ObservableProperties\n" +
                "\n" +
                "object CodeGen {\n" +
                "\n" +
                "    // todo Entity 생성\n" +
                "    fun genEntity(observableProperties: ObservableProperties): String {\n" +
                "\n" +
                "        return \"\"\n" +
                "    }\n" +
                "}"
    }
}