package com.github.wesbin.intellijplugin.ui

import com.intellij.database.model.DasColumn
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import com.intellij.openapi.ui.DialogPanel
import com.intellij.psi.PsiElement
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.Gaps
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.layout.rowWithIndent
import com.intellij.ui.layout.titledRow
import com.intellij.util.containers.JBIterable
import com.twelvemonkeys.imageio.util.IIOUtil
import java.awt.event.ActionEvent

@Tab(title = "SQL Generator")
fun SQLGenerator(psiElement: PsiElement): DialogPanel {

    val bindingProperties = BindingProperties()
    // CRUD 모드 선택
    var crud: String = "c"
    // Table Columns
    val dasColumns: JBIterable<out DasColumn> = DasUtil.getColumns(psiElement as DbTable)

    return panel {
        lateinit var createButton: Cell<JBRadioButton>
        lateinit var readButton: Cell<JBRadioButton>
        lateinit var updateButton: Cell<JBRadioButton>
        lateinit var deleteButton: Cell<JBRadioButton>

        group("CRUD") {
            panel {
                buttonGroup({ crud }, { crud = it }) {
                    row {
                        createButton = radioButton("Create", "c")
                        readButton = radioButton("Read", "r")
                        updateButton = radioButton("Update", "u")
                        deleteButton = radioButton("Delete", "d")
                    }
                }
            }
        }

        // CREATE
        groupRowsRange("INSERT INTO") {
            row {
                dasColumns.forEach { dasColumn ->
                    cell(JBCheckBox(dasColumn.name, true))
                }
            }
        }.visibleIf(createButton.selected)

        // READ
        groupRowsRange("SELECT"){
            row {
                checkBox("Column1")
            }
        }.visibleIf(readButton.selected)
        groupRowsRange("WHERE") {
            row {
                checkBox("Column1")
            }
        }.visibleIf(readButton.selected)

        // UPDATE
        groupRowsRange("SET") {
            row {
                checkBox("Column1")
            }
        }.visibleIf(updateButton.selected)

        groupRowsRange("WHERE") {
            row {
                checkBox("Column1")
            }
        }.visibleIf(updateButton.selected)

        // DELETE
        groupRowsRange("WHERE") {
            row {
                checkBox("Column1")
            }
        }.visibleIf(deleteButton.selected)

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