package app.khadga.alonzo.annotations

import com.google.auto.service.AutoService
import java.io.File
import java.lang.annotation.RetentionPolicy
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.ExecutableType
import javax.lang.model.type.TypeKind
import javax.tools.Diagnostic

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Curry

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("app.khadga.alonzo.annotations.Curry")
@SupportedOptions(CurryProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
@AutoService(Processor::class)
class CurryProcessor : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        println("In process")
        val annotatedElements = roundEnv.getElementsAnnotatedWith(Curry::class.java)
        if (annotatedElements.isEmpty()) return false

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Can't find the target directory for generated Kotlin files.")
            return false
        }

        // For each annotated method, get the arguments to the function,and the return type and then write out
        // For example, imagine fun foo(a: Int, b: String): String.  We would  write this out as:
        //    fun foo(a: Int) = { b: String ->
        //        foo(a, b)
        //    }
        for (e in annotatedElements) {
            when(e.kind) {
                ElementKind.METHOD -> {
                    val type = e.asType() as ExecutableType
                    type.parameterTypes.forEach {
                        println("param is $it")
                    }
                    val argNames = type.parameterTypes.map { it.toString() }
                    println(argNames)
                }
                else -> {}
            }
        }

//        val generatedKtFile = kotlinFile("test.generated") {
//            for (element in annotatedElements) {
//                val typeElement = element.toTypeElementOrNull() ?: continue
//
//                property("simpleClassName") {
//                    receiverType(typeElement.qualifiedName.toString())
//                    getterExpression("this::class.java.simpleName")
//                }
//            }
//        }
//

//        File(kaptKotlinGeneratedDir, "testGenerated.kt").apply {
//            parentFile.mkdirs()
//            writeText(generatedKtFile.accept(PrettyPrinter(PrettyPrinterConfiguration())))
//        }

        return true
    }

    fun extractMethodData(method: ExecutableType) {


    }
}