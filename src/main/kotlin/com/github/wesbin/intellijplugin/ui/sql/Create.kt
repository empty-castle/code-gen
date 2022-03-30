package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.ui.Tab
import com.intellij.database.model.DasColumn
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import com.intellij.openapi.ui.DialogPanel
import com.intellij.psi.PsiElement
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.util.containers.JBIterable
import java.awt.event.ActionEvent

@Tab(title = "Create")
fun tabCreate(psiElement: PsiElement): DialogPanel {

    val bindingProperties = BindingProperties()
    // CRUD 모드 선택
    var crud: String = "c"
    // Table Columns
    val dasColumns: JBIterable<out DasColumn> = DasUtil.getColumns(psiElement as DbTable)

    return panel {

        groupRowsRange("INSERT INTO") {
            row {
                dasColumns.forEach { dasColumn ->
                    cell(JBCheckBox(dasColumn.name, true))
                        .actionListener { event: ActionEvent, component: JBCheckBox ->
                            bindingProperties.text = component.text
                        }
                }
            }
        }

        group("Result") {
            row {
                textArea()
                    .horizontalAlign(HorizontalAlign.FILL)
                    .rows(20)
                    .bindText(bindingProperties::text)
            }
        }
    }
}

internal data class BindingProperties (
    var text: String = "",
)