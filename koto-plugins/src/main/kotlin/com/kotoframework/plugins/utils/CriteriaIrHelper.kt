package com.kotoframework.plugins.utils

import com.kotoframework.plugins.KotoBuildScope
import org.jetbrains.kotlin.backend.common.extensions.FirIncompatiblePluginAPI
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrVariable
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl
import org.jetbrains.kotlin.ir.types.getClass
import org.jetbrains.kotlin.ir.types.toKotlinType
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.descriptorUtil.classId

// kotlin内置函数名转换为koto函数名
val map = mapOf(
    "EQEQ" to "eq",
    "ANDAND" to "and",
    "OROR" to "or",
    "greater" to "gt",
    "less" to "lt",
    "greaterOrEqual" to "ge",
    "lessOrEqual" to "le",
    "contains" to "isIn",
)

// SimpleCriteria类
@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.simpleCriteriaClassSymbol() =
    pluginContext.referenceClass(FqName("com.kotoframework.core.condition.SimpleCriteria"))!!

// SimpleCriteria类的setCriteria函数
@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.simpleCriteriaSetterSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.core.condition.SimpleCriteria.addCriteria")).first()

//lineToHump函数
@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.lineToHumpSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.core.condition.lineToHump")).first()

//humpToLine函数
@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.humpToLineSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.core.condition.humpToLine")).first()

//criteriaField类的setCriteria函数
@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.criteriaFieldSetterSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.definition.criteriaField.setCriteria")).first()

//criteriaField类的setCriteria函数
@OptIn(FirIncompatiblePluginAPI::class)
fun KotoBuildScope.stringToConditionTypeSymbol() =
    pluginContext.referenceFunctions(FqName("com.kotoframework.enums.toConditionType")).first()

//创建val tmp = lineToHump(str)语句，用于将下划线转换为驼峰
fun KotoBuildScope.lineToHump(irExpression: IrExpression): IrGetValueImpl {
    val irCall = builder.irCall(lineToHumpSymbol())
    irCall.putValueArgument(0, irExpression)
    return builder.irGet(blockBody.irTemporary(irCall))
}

//创建val tmp = humpToLine(str)语句，用于将驼峰转换为下划线
fun KotoBuildScope.humpToLine(irExpression: IrExpression): IrGetValueImpl {
    val irCall = builder.irCall(humpToLineSymbol())
    irCall.putValueArgument(0, irExpression)
    return builder.irGet(blockBody.irTemporary(irCall))
}

// 创建域
fun IrBuilderWithScope.createBuildScope(
    blockBody: IrBlockBuilder,
    pluginContext: IrPluginContext,
    function: IrFunction
): KotoBuildScope {
    return KotoBuildScope().apply {
        builder = this@createBuildScope
        this.blockBody = blockBody
        this.function = function
        this.pluginContext = pluginContext
    }
}

fun KotoBuildScope.stringToConditionType(str: String): IrGetValueImpl {
    val irCall = builder.irCall(stringToConditionTypeSymbol())
    irCall.putValueArgument(0, blockBody.irString(str))
    return builder.irGet(blockBody.irTemporary(irCall))
}

// 创建SimpleCriteria语句，形如val tmp = SimpleCriteria(...
fun KotoBuildScope.createSimpleCriteria(
    parameterName: IrExpression,
    type: String,
    not: Boolean,
    value: IrExpression? = null,
    children: List<IrVariable> = listOf(),
    table: IrExpression? = null
): IrVariable {
    val irCall = builder.irCall(simpleCriteriaClassSymbol().constructors.first())
    irCall.putValueArgument(0, parameterName)
    irCall.putValueArgument(1, parameterName)
    irCall.putValueArgument(2, stringToConditionType(type))
    irCall.putValueArgument(3, builder.irBoolean(not))
    if (value != null) {
        irCall.putValueArgument(4, value)
    }
    if (table != null) {
        irCall.putValueArgument(5, table)
    }
    val irVariable = blockBody.irTemporary(irCall)

    blockBody.apply {
        children.forEach {
            +irCall(simpleCriteriaSetterSymbol()).apply {
                dispatchReceiver = builder.irGet(irVariable)
                putValueArgument(0, builder.irGet(it))
            }
        }
    }
    return irVariable
}

/**
 * Adds IR for setting simple criteria. example: criteriaField.setCriteria(tmp)
 *
 * @receiver KotoBuildScope instance.
 * @author sundaiyue
 */
fun KotoBuildScope.addSetSimpleCriteriaIr() {
    blockBody.apply {
        builder.irCall(criteriaFieldSetterSymbol()).apply {
            dispatchReceiver = builder.irGet(function.extensionReceiverParameter!!)
            putValueArgument(0, blockBody.irGet(buildCriteria(function.body!!)!!))
        }
    }
}

// 通过递归，分析用户编写的Criteria.()->Boolean lambda语句，解析为SimpleCriteriaIr
fun KotoBuildScope.buildCriteria(element: IrElement, setNot: Boolean = false): IrVariable? {
    var parameterName: IrExpression = builder.irString("")
    var type = "ROOT"
    var not = setNot
    var value: IrExpression? = null
    val children: MutableList<IrVariable?> = mutableListOf()
    var tableName: IrExpression? = null
    when (element) {
        is IrBlockBody -> {
            // 处理块体
            element.statements.forEach { statement ->
                children.add(buildCriteria(statement))
            }
        }

        is IrIfThenElseImpl -> {
            val origin = element.origin.toString()
            type = map[origin] ?: origin
            element.branches.forEach {
                children.add(buildCriteria(it.condition))
                children.add(buildCriteria(it.result))
            }
        }

        is IrCall -> {
            val funcName = element.funcName
            type = funcName
            val args = element.argumentsNot("CriteriaField")
            if (funcName == "not") {
                if (args.size == 1) { // !(a in b) -> a !in b
                    return buildCriteria(args[0], true)
                }
                args.forEach {
                    children.add(buildCriteria(it))
                }
            } else {
                when (funcName) {
                    "isIn" -> {
                        value = args[0]
                        parameterName = getParameterName(args[1])
                        tableName = getTableName(args[1])
                    }

                    "isNull", "notNull" -> {
                        parameterName = getParameterName(args[0])
                        tableName = getTableName(args[0])
                    }

                    "lt", "gt", "le", "ge" -> {
                        val compareToIrCall = args[0]
                        val compareToArgs = (compareToIrCall as IrCallImpl).argumentsNot("CriteriaField")
                        parameterName = getParameterName(compareToArgs[0])
                        value = compareToArgs[1]
                        tableName = getTableName(compareToArgs[0])
                    }

                    "notBetween", "notLike" -> {
                        type = funcName.replaceFirst("not", "").replaceFirstChar { it.lowercase() }
                        not = true
                        parameterName = getParameterName(args[0])
                        value = args[1]
                        tableName = getTableName(args[0])
                    }

                    "eq", "like", "between" -> {
                        parameterName = getParameterName(args[0])
                        value = args[1]
                        tableName = getTableName(args[0])
                    }
                }
            }
        }

        is IrReturn -> {
            return buildCriteria(element.value)
        }

        is IrConstImpl<*> -> {
            // EMPTY LEAF
            return null
        }
    }

    return KotoBuildScope.SimpleCriteriaIR(parameterName, type, not, value, children.filterNotNull(), tableName)
        .toIrVariable()
}

// 返回KPojo或字符串的参数名，若有注解，读取注解信息
@OptIn(ObsoleteDescriptorBasedAPI::class)
fun KotoBuildScope.getParameterName(expression: IrExpression): IrExpression {
    return when (expression) {
        is IrCall -> {
            val propName = extractGetX(expression.funcName)
            val annotations =
                expression.dispatchReceiver!!.type.getClass()!!.properties.first { it.name.asString() == propName }.annotations
            val columnAnnotation =
                annotations.firstOrNull { it.symbol.descriptor.containingDeclaration.classId == ClassId.fromString("com/kotoframework/core/annotations/Column") }
            if (columnAnnotation != null) {
                lineToHump(columnAnnotation.getValueArgument(0)!!)
            } else {
                builder.irString(propName)
            }
        }

        is IrConst<*> -> builder.irString(expression.value.toString())
        else -> builder.irString("")
    }
}

//返回Kpojo属性表名
@OptIn(ObsoleteDescriptorBasedAPI::class)
fun KotoBuildScope.getTableName(expression: IrExpression): IrExpression? {
    return when (expression) {
        is IrCall -> {
            val kClass = expression.dispatchReceiver!!.type.getClass()!!
            val annotations = kClass.annotations
            val tableAnnotation =
                annotations.firstOrNull { it.symbol.descriptor.containingDeclaration.classId == ClassId.fromString("com/kotoframework/core/annotations/Table") }
            if (tableAnnotation != null) {
                return tableAnnotation.getValueArgument(0)!!
            } else {
                humpToLine(builder.irString(kClass.name.asString()))
            }
        }

        else -> null
    }
}

// 获取koto函数名
val IrCall.funcName
    get(): String {
        val name = this.symbol.owner.name.asString()
        return (map[name] ?: name)
    }

//获取函数参数列表（去除receiver）
@OptIn(ObsoleteDescriptorBasedAPI::class)
fun IrCall.argumentsNot(field: String): List<IrExpression> {
    return getArguments().filter { (_, expression) -> expression.type.toKotlinType().toString() != field }
        .map { it.second }
}

//解析GET_PROPERTY的字符串属性
fun extractGetX(ir: String): String {
    return ir.split("<get-").last().split(">").first()
}