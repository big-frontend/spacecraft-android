package com.jamesfchen.kcp

import com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 */
//private val KSP_OPTIONS = CompilerConfigurationKey.create<MyAbstractCliOption.Builder>("Ksp options")
class MyCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "com.jamesfchen.kcp.myp-rocessor"
    override val pluginOptions: Collection<AbstractCliOption> = MyAbstractCliOption.values().asList()
    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        super.processOption(option, value, configuration)
    }

    override fun <T> CompilerConfiguration.appendList(
        option: CompilerConfigurationKey<List<T>>,
        value: T
    ) {
        TODO("Not yet implemented")
    }

    override fun <T> CompilerConfiguration.appendList(
        option: CompilerConfigurationKey<List<T>>,
        values: List<T>
    ) {
        TODO("Not yet implemented")
    }

    override fun CompilerConfiguration.applyOptionsFrom(
        map: Map<String, List<String>>,
        pluginOptions: Collection<AbstractCliOption>
    ) {
        TODO("Not yet implemented")
    }
}

class MyComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        TODO("Not yet implemented")
    }
}


enum class MyAbstractCliOption(
    override val allowMultipleOccurrences: Boolean,
    override val description: String,
    override val optionName: String,
    override val required: Boolean,
    override val valueDescription: String
) : AbstractCliOption {}