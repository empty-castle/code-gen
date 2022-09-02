package com.github.wesbin.codegen.dialog.panel

import com.github.wesbin.codegen.core.CodeGenModules
import com.intellij.application.options.ModulesComboBox
import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.refactoring.ui.PackageNameReferenceEditorCombo
import com.intellij.ui.dsl.builder.COLUMNS_LARGE
import com.intellij.ui.dsl.builder.COLUMNS_MEDIUM
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import java.awt.Component
import java.awt.event.ItemEvent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

@Suppress("UnstableApiUsage")
class TopPanel(
    private val project: Project,
    private val dataSources: List<DbDataSource>,
    override var observableProperties: ObservableProperties,
): Panel {

    companion object {
        const val RECENTS_KEY = "TopPanel.RecentsKey"
    }

    override fun createPanel(): DialogPanel {

        lateinit var tableCombobox: ComboBox<DbDataSource>

        return panel {
            row("DB connection:") {
                tableCombobox = comboBox(
                    dataSources.toTypedArray(),
                    object: JLabel(), ListCellRenderer<DbDataSource?> {
                        override fun getListCellRendererComponent(
                            list: JList<out DbDataSource>?,
                            value: DbDataSource?,
                            index: Int,
                            isSelected: Boolean,
                            cellHasFocus: Boolean,
                        ): Component {
                            if (value != null) {
                                text = value.name
                            }
                            return this
                        }
                    }
                )
                    .apply {
                        columns(COLUMNS_LARGE)
                    }
                    .component
                    .apply {
                        addItemListener {
                            if (it.stateChange == ItemEvent.SELECTED) {
                                observableProperties.selectedDbDataSource = it.item as DbDataSource
                            }
                        }
                    }
            }

            // tableCombobox 초기 선택 observableProperties 반영
            if (tableCombobox.itemCount > 0) {
                observableProperties.selectedDbDataSource = tableCombobox.item
            }

            row("Module") {
                observableProperties.modulesComboBox = cell(
                    ModulesComboBox()
                        .apply {
                            columns(COLUMNS_MEDIUM)
                            setModules(ModuleManager.getInstance(project).modules.toList())
                            selectedIndex = 0
                        }
                ).component
            }

            row("Package") {
                observableProperties.packageComboBox = cell(
                    PackageNameReferenceEditorCombo("", project, RECENTS_KEY, "Choose Package")
                        .apply {
                            setTextFieldPreferredWidth(40)
                        }
                ).component
            }

        }
    }
}