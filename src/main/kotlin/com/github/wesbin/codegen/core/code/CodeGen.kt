package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.type.TypeModule
import com.github.wesbin.codegen.dialog.observer.ObservableProperties

abstract class CodeGen {

    val typeModule: TypeModule = TypeModule.INSTANCE

    abstract val fieldModule: FieldModule
    abstract fun generate(observableProperties: ObservableProperties): String
}