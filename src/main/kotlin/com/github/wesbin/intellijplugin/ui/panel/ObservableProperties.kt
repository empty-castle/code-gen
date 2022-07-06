package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.database.psi.DbDataSource
import kotlin.properties.Delegates

class ObservableProperties {

    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) {property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue?.name)
        }
    }

    var selectedSourceRoot: String by Delegates.observable("") {property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue)
        }
    }
}