package com.github.wesbin.codegen.core.codeModule

import com.github.wesbin.codegen.dialog.panel.ObservableProperties

interface CodeModule {

    fun generate(observableProperties: ObservableProperties): String
}