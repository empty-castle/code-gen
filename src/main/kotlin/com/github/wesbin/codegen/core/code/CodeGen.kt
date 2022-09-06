package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.CodeGenModules
import com.github.wesbin.codegen.dialog.panel.ObservableProperties

interface CodeGen {

    fun generate(codeGenModules: CodeGenModules, observableProperties: ObservableProperties): String
}