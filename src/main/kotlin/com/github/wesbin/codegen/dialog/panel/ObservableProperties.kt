package com.github.wesbin.codegen.dialog.panel

import com.intellij.database.model.DasObject
import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import kotlin.properties.Delegates

class ObservableProperties() {

    lateinit var leftPanel: LeftPanel
    lateinit var rightPanel: RightPanel

    // DB 정보
    var selectedDbDataSource: DbDataSource? by Delegates.observable(null) {property, oldValue, newValue ->
        if (oldValue != newValue) {
            leftPanel.update(property, newValue as Any)
        }
    }

    // 파일 생성 위치 정보
    var selectedSourceRoot: TextFieldWithBrowseButton? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            println(newValue)
        }
    }

    // 테이블 정보
    var selectedTable: DasObject? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            rightPanel.update(property, newValue as Any)
        }
    }
}