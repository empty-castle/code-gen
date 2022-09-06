package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.CodeGenModules
import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.dialog.panel.ObservableProperties

class ModelCodeGen private constructor(): CodeGen {

    companion object {
        val INSTANCE = ModelCodeGen()
    }

    override fun generate(codeGenModules: CodeGenModules, observableProperties: ObservableProperties): String {
        val packageName: String = observableProperties.packageComboBox!!.text

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<Field> = mutableListOf()



        var result = """"""
        return result
    }
}