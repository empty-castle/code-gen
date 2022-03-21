package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*

@Demo(
    title = "Gaps",
    description = "<br>\u2022 Horizontal gaps are managed by Cell.gap method. Between label and related component RightGap.SMALL is used, " + "otherwise medium gap is used (there is no such enum value)<br>" + "\u2022 Left indent can be added by Panel.indent<br>" + "\u2022 For two columns mode use Panel.twoColumnsRow or RightGap.COLUMNS<br>" + "\u2022 Vertical gaps are absent by default (there is minimum space between components) and can be added by Row.topGap " + "and Row.bottomGap methods. Such gaps should be appended to 'related' rows " + "(so if row is hidden with gaps remaining layout is not broken). " + "Note that some elements like Panel.group can add additional space around",
    scrollbar = true
)
fun demoGaps(): DialogPanel {
    return panel {
        group("Examples when RightGap.SMALL is needed") {
            row {
                val checkBox = checkBox("Use mail:")
                    .gap(RightGap.SMALL)
                textField()
                    .enabledIf(checkBox.selected)
            }.rowComment("gaps after check boxes/radio buttons when they are intended to be labels")
            row("Width:") {
                textField()
                    .gap(RightGap.SMALL)
                label("pixels")
            }.rowComment("gaps between related components like text field and label after it")
        }

        group("Panel.indent") {
            row {
                label("Not indented row")
            }
            indent {
                row {
                    label("Indented row, Panel.group and Panel.groupRowsRange are more useful often")
                }
            }
        }

        group("Two column mode") {
            twoColumnsRow({
                checkBox("First column")
            }, {
                checkBox("Second column")
            }).rowComment("Panel.twoColumnsRow is used")
            row {
                checkBox("Column 1")
                    .gap(RightGap.COLUMNS)
                checkBox("Column 2")
            }.layout(RowLayout.PARENT_GRID)
                .rowComment("RightGap.COLUMNS is used")
        }

        group("Examples for verical gaps") {
            row {
                checkBox("Option 1")
            }
            row {
                checkBox("Option 2")
            }
            row {
                checkBox("Option unrelated to previous two options")
            }.topGap(TopGap.MEDIUM)
        }
    }
}