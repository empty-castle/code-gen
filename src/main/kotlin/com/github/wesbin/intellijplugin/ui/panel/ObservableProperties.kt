package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import kotlin.properties.Delegates

class ObservableProperties {

    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) {property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue?.name)
        }
    }

    var selectedSourceRoot: TextFieldWithBrowseButton? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue)
        }
    }
}