package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.dialog.panel.ObservableProperties

interface CodeGeneratorModule {

    fun generate(observableProperties: ObservableProperties): String
}