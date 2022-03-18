package com.github.wesbin.intellijplugin.ui

import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
@Target(AnnotationTarget.FUNCTION)
internal annotation class Demo(
    val title: String,
    val description: String,
    val scrollbar: Boolean = false
)