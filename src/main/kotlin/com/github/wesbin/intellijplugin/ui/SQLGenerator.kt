package com.github.wesbin.intellijplugin.ui

import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import com.intellij.openapi.ui.DialogPanel
import com.intellij.psi.PsiElement
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.rows
import com.intellij.ui.dsl.builder.text
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign

@Tab(title = "SQL Generator")
fun SQLGenerator(psiElement: PsiElement): DialogPanel {
    return panel {
        group("Columns") {
            row {
                checkBox("All")
            }
            indent {
                rowsRange {
                    row {
                        for (dasColumn in DasUtil.getColumns(psiElement as DbTable)) {
                            checkBox(dasColumn.name)
                        }
                    }
                }
            }
        }

        group("Result") {
            row {
                textArea()
                    .text("Test")
                    .horizontalAlign(HorizontalAlign.FILL)
                    .rows(10)
            }
        }
    }
}