package com.kotoframework.plugins

import com.kotoframework.plugins.transformer.CriteriaParserTransformer
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.*

class CriteriaParserExtension(
    val supportK2: Boolean
) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        moduleFragment.transform(CriteriaParserTransformer(pluginContext), null)
        print(
            moduleFragment.dumpKotlinLike(
                KotlinLikeDumpOptions(
                    true, true, true, true, LabelPrintingStrategy.ALWAYS, FakeOverridesStrategy.ALL, false
                )
            )
//            moduleFragment.dump()
        )
    }
}