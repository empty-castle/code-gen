package com.github.wesbin.intellijplugin.dialog.panel

import kotlin.reflect.KProperty

interface Observer {
    fun update(property: KProperty<*>, newValue: Any?)
}