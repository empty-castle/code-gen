package com.github.wesbin.intellijplugin.ui.panel

import kotlin.reflect.KProperty

interface Observer {
    fun update(property: KProperty<*>, newValue: Any?)
}