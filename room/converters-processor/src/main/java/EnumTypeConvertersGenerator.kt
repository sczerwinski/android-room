/*
 * Copyright 2020 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.czerwinski.android.room.converters.processor

import it.czerwinski.android.room.converters.GenerateEnumTypeConverter
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class EnumTypeConvertersGenerator : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(annotationClass.canonicalName)

    override fun process(annotations: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        val outputDir = requireNotNull(processingEnv.options[OPTION_KAPT_OUTPUT_DIR])
        roundEnvironment.getElementsAnnotatedWith(annotationClass)
            .map { element -> createEnumMetadata(element, outputDir) }
            .forEach { enumMetadata -> generateEnumTypeConverter(enumMetadata) }
        return true
    }

    private fun createEnumMetadata(element: Element, outputDir: String): EnumMetadata = EnumMetadata(
        packageName = processingEnv.elementUtils.getPackageOf(element).toString(),
        enumSimpleName = element.simpleName.toString(),
        type = element.getAnnotation(annotationClass).type,
        outputDir = outputDir
    )

    private fun generateEnumTypeConverter(metadata: EnumMetadata) {
        EnumTypeConverterPoet.generateTypeConverter(metadata)
    }

    companion object {
        private const val OPTION_KAPT_OUTPUT_DIR = "kapt.kotlin.generated"

        private val annotationClass = GenerateEnumTypeConverter::class.java
    }
}
