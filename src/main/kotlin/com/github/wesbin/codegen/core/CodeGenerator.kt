package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.entity.EntityField
import com.github.wesbin.codegen.core.entity.EntityFieldFactory
import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

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