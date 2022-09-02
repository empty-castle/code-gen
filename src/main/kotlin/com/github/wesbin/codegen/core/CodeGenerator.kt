package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.EntityCodeModule
import com.github.wesbin.codegen.core.codeModule.ModelCodeModule
import com.github.wesbin.codegen.dialog.panel.ObservableProperties

class CodeGenerator(actionId: String) {

    private var generatorModule: CodeModule?

    init {
         generatorModule =
            when (actionId.split(".")[1]) {
                "entity" -> EntityCodeModule.INSTANCE
                "model" -> ModelCodeModule.INSTANCE
                else -> null
            }
    }

    fun generate(observableProperties: ObservableProperties): String {
        return generatorModule!!.generate(observableProperties)
    }
}