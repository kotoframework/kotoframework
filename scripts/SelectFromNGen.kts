import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val N = 1
val className = "SelectFrom${N.takeIf { N > 1 } ?: ""}"
val path = "../koto-core/src/main/kotlin/com/kotoframework/orm/query/actions/$className-$N.kt"
val author = "OUSC"
val generics = (1..N).map { "T$it" }
val template = """package com.kotoframework.orm.query.actions
    
${
    """import com.kotoframework.SQL
import com.kotoframework.utils.tableAlias""".takeIf { N > 1 } ?: ""
}
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin${N + 1}
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by $author on ${SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().time)}
 */
 open class $className<${generics.joinToString { "$it: KPojo" }}>(${generics.joinToString { "override var ${it.lowercase()}: $it? = null, " }}val kJdbcWrapper: KJdbcWrapper? = null): QueryAction$N<${
    generics.joinToString(", ")
}>(
     ${(1..16).toList().joinToString(",\n") { i -> "t$i".takeIf { i <= N } ?: "unknown" }},
     kJdbcWrapper
 ) {

    ${"""init { super.initTables() }""".takeIf { N == 1 } ?: ""}

    override fun select(vararg fields: Field): $className<${generics.joinToString(", ")}> {
        return super.select(*fields) as $className<${generics.joinToString(", ")}>
    }
 }
""".trimIndent()

File(path).writeText(template)