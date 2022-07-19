package com.github.wesbin.intellijplugin.dialog.panel

import com.intellij.database.model.DasObject
import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import kotlin.properties.Delegates

class ObservableProperties() {

    lateinit var leftPanel: LeftPanel
    lateinit var rightPanel: RightPanel

    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) {property, oldValue, newValue ->
        if (oldValue != newValue) {
            leftPanel.update(property, newValue as Any)
        }
    }

    var selectedSourceRoot: TextFieldWithBrowseButton? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue)
        }
    }

    var selectedTable: DasObject? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            rightPanel.update(property, newValue as Any)
        }
    }
}