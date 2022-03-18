package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.panel

@Demo(title = "Components Labels",
    description = "When modifiable components have text labels they must be connected together by one of two possible ways: " +
            "Row(\"label\") or Cell.label(\"label\")")
fun demoComponentLabels(): DialogPanel {
    return panel {
        row("Row label:") {
            textField()
            textField()
                .label("Cell label at left:")
        }

        row {
            textField()
                .label("Cell label at top:", LabelPosition.TOP)
        }

        group("CheckBox/RadioButton labels") {
            row("Row1:") {
                checkBox("Checkbox")
            }
            row("Row2:") {
                textField()
            }
            row {
                comment(
                    "According to <a href='https://jetbrains.design/intellij/principles/layout/#checkboxes-and-radio-buttons'>UI Guidelines</a> " +
                            "space after labels is increased if CheckBox/RadioButton is used"
                )
            }
        }
    }
}