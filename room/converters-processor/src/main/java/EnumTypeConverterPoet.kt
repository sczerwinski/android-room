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

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

object EnumTypeConverterPoet {

    fun generateTypeConverter(metadata: EnumMetadata) {
        FileSpec.builder(
            packageName = metadata.packageName,
            fileName = metadata.typeConverterSimpleName
        )
            .addType(generateTypeConverterObject(metadata, EnumTypeConverterMethodsPoet.forEnumType(metadata.type)))
            .build()
            .writeTo(File(metadata.outputDir))
    }

    private fun generateTypeConverterObject(
        metadata: EnumMetadata,
        methodsPoet: EnumTypeConverterMethodsPoet
    ): TypeSpec =
        TypeSpec.classBuilder(metadata.typeConverterClassName)
            .addFunction(methodsPoet.generateFromEnumMethod(metadata))
            .addFunction(methodsPoet.generateToEnumMethod(metadata))
            .build()
}
