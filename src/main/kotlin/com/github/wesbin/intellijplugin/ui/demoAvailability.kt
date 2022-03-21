package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected

@Demo(title = "Availability", description = "Visibility and enabled state of panels, rows and cells can be bound to another components and applied automatically")
fun demoAvailability(): DialogPanel {
    return panel {
        group("Enabled") {
            lateinit var checkBox: Cell<JBCheckBox>
            row {
                checkBox = checkBox("Check to enable options")
            }
            indent {
                rowsRange {
                    row {
                        checkBox("Options 1")
                    }
                    row {
                        checkBox("Options 2")
                    }
                }.enabledIf(checkBox.selected)
            }
        }

        group("Visible") {
            lateinit var checkBox: Cell<JBCheckBox>
            row {
                checkBox = checkBox("Check to show options")
            }
            indent {
                rowsRange {
                    row {
                        checkBox("Options 1")
                    }
                    row {
                        checkBox("Options 2")
                    }
                }.visibleIf(checkBox.selected)
            }
        }
    }
}