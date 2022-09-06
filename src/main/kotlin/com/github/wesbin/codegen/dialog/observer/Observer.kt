package com.github.wesbin.codegen.dialog.observer

import kotlin.reflect.KProperty

interface Observer {
    fun update(property: KProperty<*>, newValue: Any?)
}