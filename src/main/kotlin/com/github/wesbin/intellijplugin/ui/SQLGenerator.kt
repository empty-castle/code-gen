package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.psi.PsiElement
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign

@Tab(title = "SQL Generator")
fun SQLGenerator(psiElement: PsiElement): DialogPanel {

    val bindingProperties = BindingProperties()
    // CRUD 모드 선택
    var crud = ""


    return panel {
        lateinit var readButton: Cell<JBRadioButton>
        group("CRUD") {
            panel {
                buttonGroup({ crud }, { crud = it }) {
                    row {
                        radioButton("Create", "c")
                        readButton = radioButton("Read", "r")
                        radioButton("Update", "u")
                        radioButton("Delete", "d")
                    }
                }
            }
        }

        group("SELECT") {
            row {
                checkBox("Column1")
            }
        }

        groupRowsRange("WHERE") {
            row {
                checkBox("Column1")
            }
        }.visibleIf(readButton.selected)

        group("Result") {
            row {
                textArea()
                    .horizontalAlign(HorizontalAlign.FILL)
                    .rows(20)
            }
        }
    }
}

internal data class BindingProperties (
    var checkBox: Boolean = true,
)
