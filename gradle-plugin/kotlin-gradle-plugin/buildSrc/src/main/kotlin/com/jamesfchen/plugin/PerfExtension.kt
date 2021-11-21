package com.jamesfchen.plugin

import org.gradle.api.provider.Property

interface  PerfExtension {
    val  disable: Property<Boolean>
}