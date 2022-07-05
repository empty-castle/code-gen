package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

@Suppress("UnstableApiUsage")
class TopPanel(private val dataSources: List<DbDataSource>): Panel {

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
                    .horizontalAlign(HorizontalAlign.FILL)
            }

            row("Source root:") {
                textField()
                    .horizontalAlign(HorizontalAlign.FILL)
            }
        }
    }
}