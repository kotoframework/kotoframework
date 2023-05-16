package com.kotoframework.plugins.test

import com.kotoframework.plugins.CriteriaParserCompilerPluginRegistrar
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class CriteriaParserTest {
  @OptIn(ExperimentalCompilerApi::class)
  @Test
  fun `IR plugin success`() {
//    val result = compile(
//      sourceFile = SourceFile.fromPath(File("/Users/zhangzhengtian/Github/KtIRPlugin/src/test/kotlin/com/sankuai/demo/test/main.kt"))
//    )
    val result = compile(
      sourceFile = SourceFile.kotlin("main.kt", """ 
      import com.kotoframework.core.annotations.Column
      import com.kotoframework.interfaces.KPojo
      import com.kotoframework.orm.query.from
      
      data class User(
          val id: Int? = null,
          @Column("user_name")
          val name: String? = null,
          val age: Int? = null,
      ) : KPojo
      
      fun main() {
          from<User>().select {
              it.id + it.name + "max(id)" + "count(id)" + "age" + "update_time as updateTime" + "create_time as createTime"
          }
      }
      """.trimIndent())
    )
    assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

    val ktClazz = result.classLoader.loadClass("MainKt")
    val main = ktClazz.declaredMethods.single { it.name == "main" && it.parameterCount == 0 }
    main.invoke(null)
  }

  @OptIn(ExperimentalCompilerApi::class)
  fun compile(
    sourceFiles: List<SourceFile>,
    plugin: CompilerPluginRegistrar = CriteriaParserCompilerPluginRegistrar(),
  ): KotlinCompilation.Result {
    return KotlinCompilation().apply {
      sources = sourceFiles
      useIR = true
      compilerPluginRegistrars = listOf(plugin)
      inheritClassPath = true
    }.compile()
  }

  @OptIn(ExperimentalCompilerApi::class)
  fun compile(
    sourceFile: SourceFile,
    plugin: CompilerPluginRegistrar = CriteriaParserCompilerPluginRegistrar(),
  ): KotlinCompilation.Result {
    return compile(listOf(sourceFile), plugin)
  }
}