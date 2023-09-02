package com.kotoframework.plugins.utils

import com.kotoframework.definition.SelectField
import com.kotoframework.plugins.KotoBuildScope
import org.jetbrains.kotlin.backend.common.extensions.FirIncompatiblePluginAPI
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl
import org.jetbrains.kotlin.ir.types.getClass
import org.jetbrains.kotlin.ir.util.properties
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.descriptorUtil.classId

@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.addOrderByFieldSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.definition.OrderByField.addFields")).first()


fun KotoBuildScope.setOrderByFields(): List<IrCall> {
    val receiver = function.extensionReceiverParameter!! // this
    val variables = getOrderByFields(function.body!!)
    return variables.map {
        builder.irCall(addOrderByFieldSymbol()).apply {
            dispatchReceiver = builder.irGet(receiver)
            putValueArgument(0, it)
        }
    }
}


fun KotoBuildScope.getOrderByFields(element: IrElement): List<IrExpression> {
    val variables = mutableListOf<IrExpression>()
    when (element) {
        is IrBlockBody -> {
            // 处理块体
            element.statements.forEach { statement ->
                variables.addAll(getOrderByFields(statement))
            }
        }

        is IrCall -> {
            val args = element.argumentsNot(SelectField::class)
            if (element.funcName == "plus") {
                args.forEach {
                    if (it is IrCall) {
                        if (it.funcName == "plus") {
                            variables.addAll(getOrderByFields(it))
                        }
                        if (it.funcName == "desc" || it.funcName == "asc") {
                            variables.add(getFieldName(it))
                        }
                        if (it.origin.toString() == "GET_PROPERTY") {
                            variables.add(getFieldName(it))
                        }
                    } else {
                        variables.addAll(getOrderByFields(it))
                    }
                }
            }
        }

        is IrGetValueImpl, is IrConst<*> -> {
            variables.add(element as IrExpression)
        }

        is IrReturn -> {
            return getOrderByFields(element.value)
        }
    }
    return variables
}