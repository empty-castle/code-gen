package com.github.wesbin.codegen.core.codeModule

import com.github.wesbin.codegen.core.field.Field
import com.github.wesbin.codegen.dialog.panel.ObservableProperties

class ModelCodeModule: CodeGeneratorModule {

    override fun generate(observableProperties: ObservableProperties): String {
        val packageName: String = observableProperties.packageComboBox!!.text

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<Field> = mutableListOf()



        var result = """"""
        return result
    }
}