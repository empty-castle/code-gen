package com.github.wesbin.intellijplugin.ui

import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
@Target(AnnotationTarget.FUNCTION)
internal annotation class Tab(
    val title: String
)
