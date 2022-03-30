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

@Tab(title = "Update")
fun tabUpdate(psiElement: PsiElement): DialogPanel {

    // Table Columns
    val dasColumns: JBIterable<out DasColumn> = DasUtil.getColumns(psiElement as DbTable)

    return panel {
        groupRowsRange("SET") {
            row {
                dasColumns.forEach { dasColumn ->
                    cell(JBCheckBox(dasColumn.name, true))
                }
            }
        }

        groupRowsRange("WHERE") {
            row {
                dasColumns.forEach { dasColumn ->
                    cell(JBCheckBox(dasColumn.name, true))
                }
            }
        }

        group("Result") {
            row {
                textArea()
                    .horizontalAlign(HorizontalAlign.FILL)
                    .rows(20)
            }
        }
    }
}