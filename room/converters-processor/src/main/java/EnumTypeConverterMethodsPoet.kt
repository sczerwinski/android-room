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

import androidx.room.TypeConverter
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.STRING
import it.czerwinski.android.room.converters.EnumType

interface EnumTypeConverterMethodsPoet {
    fun generateFromEnumMethod(metadata: EnumMetadata): FunSpec
    fun generateToEnumMethod(metadata: EnumMetadata): FunSpec

    companion object {
        fun forEnumType(type: EnumType): EnumTypeConverterMethodsPoet = when (type) {
            EnumType.STRING -> StringEnumTypeConverterMethodsPoet
            EnumType.ORDINAL -> OrdinalEnumTypeConverterMethodsPoet
        }
    }
}

abstract class AbstractEnumTypeConverterMethodsPoet : EnumTypeConverterMethodsPoet {

    protected abstract val convertType: ClassName

    protected abstract val codeBlockFromFormat: String
    protected abstract val codeBlockToFormat: String

    override fun generateFromEnumMethod(metadata: EnumMetadata): FunSpec =
        FunSpec.builder("from${metadata.enumSimpleName}")
            .addAnnotation(TypeConverter::class.java)
            .addParameter(metadata.enumSimpleName.decapitalize(), metadata.nullableEnumTypeName)
            .addStatement(codeBlockFromFormat, metadata.enumSimpleName.decapitalize())
            .returns(convertType.copy(nullable = true))
            .build()

    override fun generateToEnumMethod(metadata: EnumMetadata): FunSpec =
        FunSpec.builder("to${metadata.enumSimpleName}")
            .addAnnotation(TypeConverter::class.java)
            .addParameter(convertType.simpleName.decapitalize(), convertType.copy(nullable = true))
            .addStatement(codeBlockToFormat, convertType.simpleName.decapitalize(), metadata.enumSimpleName)
            .returns(metadata.nullableEnumTypeName)
            .build()
}

object StringEnumTypeConverterMethodsPoet : AbstractEnumTypeConverterMethodsPoet() {
    override val convertType: ClassName = STRING
    override val codeBlockFromFormat: String = "return %N?.name"
    override val codeBlockToFormat: String = "return %N?.let(%N::valueOf)"
}

object OrdinalEnumTypeConverterMethodsPoet : AbstractEnumTypeConverterMethodsPoet() {
    override val convertType: ClassName = INT
    override val codeBlockFromFormat: String = "return %N?.ordinal"
    override val codeBlockToFormat: String = "return %N?.let { %N.values()[it] }"
}
