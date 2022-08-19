package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.dialog.panel.ObservableProperties

class CodeGenerator(actionId: String) {

    private var generatorModule: CodeGeneratorModule?

    init {
         generatorModule =
            when (actionId.split(".")[1]) {
                "entity" -> EntityCodeModule()
                "model" -> ModelCodeModule()
                else -> null
            }
    }

    fun generate(observableProperties: ObservableProperties): String {
        return generatorModule!!.generate(observableProperties)
    }
}