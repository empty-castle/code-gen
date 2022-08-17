package com.github.wesbin.codegen.dialog.panel

import com.intellij.application.options.ModulesComboBox
import com.intellij.database.model.DasObject
import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.module.Module
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.refactoring.ui.PackageNameReferenceEditorCombo
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
//    PsiPackageImpl (PsiManager.getInstance(project), observableProperties.packageComboBox.text).directories[0]

    var className: String = ""

    fun checkValues(): Boolean =
        selectedDbDataSource != null
            && selectedTable != null
            && modulesComboBox != null
            && packageComboBox != null
            && className.isNotEmpty()
}