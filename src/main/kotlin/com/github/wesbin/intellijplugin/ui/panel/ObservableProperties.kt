package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import kotlin.properties.Delegates

class ObservableProperties(private val observers: List<Observer>) {

    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) {property, oldValue, newValue ->
        if (oldValue != newValue) {
            observers.forEach(Observer::update)
        }
    }

    var selectedSourceRoot: TextFieldWithBrowseButton? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue)
        }
    }
}