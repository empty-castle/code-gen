package com.github.wesbin.codegen.dialog.panel

import com.intellij.database.model.DasObject
import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.module.Module
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import kotlin.properties.Delegates

class ObservableProperties() {

    lateinit var leftPanel: LeftPanel
    lateinit var rightPanel: RightPanel

    // DB 정보
    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            leftPanel.update(property, newValue as Any?)
        }
    }

    // 선택 모듈 정보
    var selectedSourceRoot: Pair<Module, String>? = null

    // 선택 경로
    var selectedPackage: TextFieldWithBrowseButton? = null

    // 테이블 정보
    var selectedTable: DasObject? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            rightPanel.update(property, newValue as Any?)
        }
    }

    var className: String = ""

    fun checkValues(): Boolean =
        selectedDbDataSource != null
            && selectedSourceRoot != null
            && selectedPackage != null
            && selectedTable != null
            && className.isNotEmpty()
}