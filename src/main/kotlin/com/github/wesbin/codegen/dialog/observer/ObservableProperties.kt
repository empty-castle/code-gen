package com.github.wesbin.codegen.dialog.observer

import com.github.wesbin.codegen.dialog.panel.LeftPanel
import com.github.wesbin.codegen.dialog.panel.RightPanel
import com.intellij.application.options.ModulesComboBox
import com.intellij.database.model.DasObject
import com.intellij.database.psi.DbDataSource
import com.intellij.refactoring.ui.PackageNameReferenceEditorCombo
import kotlin.properties.Delegates

class ObservableProperties(actionId: String) {

    lateinit var leftPanel: LeftPanel
    lateinit var rightPanel: RightPanel

    // DB 정보
    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            leftPanel.update(property, newValue as Any?)
        }
    }

    // 테이블 정보
    var selectedTable: DasObject? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            rightPanel.update(property, newValue as Any?)
        }
    }

    // 모듈 정보
    var modulesComboBox: ModulesComboBox? = null

    // 패키지 정보
    var packageComboBox: PackageNameReferenceEditorCombo? = null

    var className: String = ""

    fun checkValues(): Boolean =
        selectedDbDataSource != null
            && selectedTable != null
            && modulesComboBox != null
            && packageComboBox != null
            && className.isNotEmpty()
}