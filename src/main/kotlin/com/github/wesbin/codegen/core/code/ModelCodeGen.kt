package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.field.model.ModelFieldModule
import com.github.wesbin.codegen.dialog.observer.ObservableProperties

class ModelCodeGen(): CodeGen() {

    override val fieldModule: FieldModule = ModelFieldModule.INSTANCE

    override fun generate(observableProperties: ObservableProperties): String {
        val packageName: String = observableProperties.packageComboBox!!.text

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<Field> = mutableListOf()



        var result = """"""
        return result
    }
}