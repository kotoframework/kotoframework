package com.kotoframework.plugins

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class CriteriaCommandLineProcessor : CommandLineProcessor {
    companion object {
        private const val OPTION_SUPPORT_K2 = "supportK2"

        val ARG_OPTION_SUPPORT_K2 = CompilerConfigurationKey<Boolean>(OPTION_SUPPORT_K2)
    }

    override val pluginId: String = "com.kotoframework.plugins.criteria-parser-plugin"

    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = OPTION_SUPPORT_K2,
            valueDescription = "true or false",
            description = "support kotlin k2 compiler",
            required = false,
        )
    )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        println("processOption:: option=$option value=$value")
        return when (option.optionName) {
            OPTION_SUPPORT_K2 -> configuration.put(ARG_OPTION_SUPPORT_K2, value.lowercase() == "true")
            else -> throw IllegalArgumentException("Unexpected config option ${option.optionName}")
        }
    }

}