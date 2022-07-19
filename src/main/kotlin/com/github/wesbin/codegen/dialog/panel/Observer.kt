package com.github.wesbin.codegen.dialog.panel

import kotlin.reflect.KProperty

interface Observer {
    fun update(property: KProperty<*>, newValue: Any?)
}