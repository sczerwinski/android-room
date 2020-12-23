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

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier

object EnumTypeConverterPoet {

    fun generateTypeConverter(metadata: EnumMetadata, filer: Filer) {
        JavaFile.builder(
            metadata.packageName,
            generateTypeConverterObject(metadata, EnumTypeConverterMethodsPoet.forEnumType(metadata.type))
        )
            .build()
            .writeTo(filer)
    }

    private fun generateTypeConverterObject(
        metadata: EnumMetadata,
        methodsPoet: EnumTypeConverterMethodsPoet
    ): TypeSpec =
        TypeSpec.classBuilder(metadata.typeConverterClassName)
            .addModifiers(Modifier.PUBLIC)
            .addMethod(methodsPoet.generateFromEnumMethod(metadata))
            .addMethod(methodsPoet.generateToEnumMethod(metadata))
            .build()
}
