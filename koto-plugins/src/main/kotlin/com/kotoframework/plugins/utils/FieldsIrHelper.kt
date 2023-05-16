package com.kotoframework.plugins.utils

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
fun KotoBuildScope.addFieldSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.definition.SelectField.addFields")).first()


fun KotoBuildScope.addSelectFields() {
    val receiver = function.extensionReceiverParameter!! // this
    val variables = getFields(function.body!!)
    val irCalls = mutableListOf<IrCall>()
    variables.forEach {
        val irCall = builder.irCall(addFieldSymbol())
        irCall.dispatchReceiver = builder.irGet(receiver)
        irCall.putValueArgument(0, it)
        irCalls.add(irCall)
    }
    blockBody.apply {
        irCalls.forEach {
            +it
        }
    }
}


fun KotoBuildScope.getFields(element: IrElement): List<IrExpression> {
    val variables = mutableListOf<IrExpression>()
    when (element) {
        is IrBlockBody -> {
            // 处理块体
            element.statements.forEach { statement ->
                variables.addAll(getFields(statement))
            }
        }

        is IrCall -> {
            val args = element.argumentsNot("SelectField")
            if (element.funcName == "plus") {
                args.forEach {
                    if (it is IrCall) {
                        if (it.funcName == "plus") {
                            variables.addAll(getFields(it))
                        }
                        if (it.origin.toString() == "GET_PROPERTY") {
                            variables.add(getFieldName(it))
                        }
                    } else {
                        variables.addAll(getFields(it))
                    }
                }
            }
        }

        is IrGetValueImpl, is IrConst<*> -> {
            variables.add(element as IrExpression)
        }

        is IrReturn -> {
            return getFields(element.value)
        }
    }
    return variables
}


// 返回KPojo或字符串的参数名，若有注解，读取注解信息
@OptIn(ObsoleteDescriptorBasedAPI::class)
fun KotoBuildScope.getFieldName(expression: IrExpression): IrExpression {
    return when (expression) {
        is IrCall -> {
            val propName = extractGetX(expression.funcName)
            val annotations =
                expression.dispatchReceiver!!.type.getClass()!!.properties.first { it.name.asString() == propName }.annotations
            val columnAnnotation =
                annotations.firstOrNull { it.symbol.descriptor.containingDeclaration.classId == ClassId.fromString("com/kotoframework/core/annotations/Column") }
            if (columnAnnotation != null) {
                columnAnnotation.getValueArgument(0)!!
            } else {
                humpToLine(builder.irString(propName))
            }
        }

        else -> builder.irString("")
    }
}