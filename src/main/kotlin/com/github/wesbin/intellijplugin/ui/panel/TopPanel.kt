package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.COLUMNS_LARGE
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import java.awt.Component
import java.awt.event.ItemEvent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

@Suppress("UnstableApiUsage")
class TopPanel(
    private val dataSources: List<DbDataSource>,
    private var observableProperties: ObservableProperties
    ): Panel {

    override fun createPanel(): DialogPanel {

        return panel {
            row("DB connection:") {
                comboBox(
                    dataSources.toTypedArray(),
                    object: JLabel(), ListCellRenderer<DbDataSource?> {
                        override fun getListCellRendererComponent(
                            list: JList<out DbDataSource>?,
                            value: DbDataSource?,
                            index: Int,
                            isSelected: Boolean,
                            cellHasFocus: Boolean
                        ): Component {
                            if (value != null) {
                                // why? value.localDataSource?.url ?: ""
                                text = value.name
                            }
                            return this
                        }
                    }
                )
                    .apply {
                        columns(COLUMNS_LARGE)
                        bindItem(
                            { this.component.item },
                            { dbDataSource -> observableProperties.selectedDbDataSource = dbDataSource }
                        )
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

            row("Source root:") {
                textFieldWithBrowseButton(fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor())
                    .columns(COLUMNS_LARGE)
                    .component
                    .apply {
                        addBrowseFolderListener {
                            println(it)
                        }
                    }
            }
        }
    }
}