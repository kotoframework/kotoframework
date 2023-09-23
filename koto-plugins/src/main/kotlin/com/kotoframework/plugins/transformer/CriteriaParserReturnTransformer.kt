package com.kotoframework.plugins.transformer

import com.kotoframework.plugins.utils.createBuildScope
import com.kotoframework.plugins.utils.setSimpleCriteriaIr
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.builders.irBlock
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.builders.irTemporary
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrReturn

class CriteriaParserReturnTransformer(
    private val pluginContext: IrPluginContext,
    private val irFunction: IrFunction
) : IrElementTransformerVoidWithContext() {

    override fun visitReturn(expression: IrReturn): IrExpression {
        if (expression.returnTargetSymbol != irFunction.symbol) //只 transform 目标函数
            return super.visitReturn(expression)

        return DeclarationIrBuilder(pluginContext, irFunction.symbol).irBlock {
//            if (irFunction.returnType == pluginContext.irBuiltIns.unitType) {
//                +createBuildScope(this, pluginContext, irFunction)
//                    .setSimpleCriteriaIr()
//                return@irBlock
//            }

            val result = irTemporary(expression.value) //保存返回表达式
            +createBuildScope(this, pluginContext, irFunction)
                .setSimpleCriteriaIr()
            +expression.apply {
                value = irGet(result)
            }
        }
    }

}