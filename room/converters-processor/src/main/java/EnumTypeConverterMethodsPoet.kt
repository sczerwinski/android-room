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

import androidx.annotation.Nullable
import androidx.room.TypeConverter
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import it.czerwinski.android.room.converters.EnumType
import java.util.Locale

interface EnumTypeConverterMethodsPoet {
    fun generateFromEnumMethod(metadata: EnumMetadata): MethodSpec
    fun generateToEnumMethod(metadata: EnumMetadata): MethodSpec

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

    override fun generateFromEnumMethod(metadata: EnumMetadata): MethodSpec {
        val paramName = metadata.enumSimpleName.decapitalize(Locale.getDefault())
        return MethodSpec.methodBuilder("from${metadata.enumSimpleName}")
            .addAnnotation(TypeConverter::class.java)
            .addAnnotation(Nullable::class.java)
            .addParameter(
                ParameterSpec.builder(metadata.enumTypeName, paramName)
                    .addAnnotation(Nullable::class.java)
                    .build()
            )
            .addStatement(codeBlockFromFormat, paramName, paramName)
            .returns(convertType)
            .build()
    }

    override fun generateToEnumMethod(metadata: EnumMetadata): MethodSpec {
        val paramName = convertType.simpleName().decapitalize(Locale.getDefault())
        return MethodSpec.methodBuilder("to${metadata.enumSimpleName}")
            .addAnnotation(TypeConverter::class.java)
            .addAnnotation(Nullable::class.java)
            .addParameter(
                ParameterSpec.builder(convertType, paramName)
                    .addAnnotation(Nullable::class.java)
                    .build()
            )
            .addStatement(codeBlockToFormat, paramName, metadata.enumSimpleName, paramName)
            .returns(metadata.enumTypeName)
            .build()
    }
}

object StringEnumTypeConverterMethodsPoet : AbstractEnumTypeConverterMethodsPoet() {
    override val convertType: ClassName = ClassName.get(String::class.java)
    override val codeBlockFromFormat: String = "return \$N != null ? \$N.name() : null"
    override val codeBlockToFormat: String = "return \$N != null ? \$N.valueOf(\$N) : null"
}

object OrdinalEnumTypeConverterMethodsPoet : AbstractEnumTypeConverterMethodsPoet() {
    override val convertType: ClassName = ClassName.get(Integer::class.java)
    override val codeBlockFromFormat: String = "return \$N != null ? \$N.ordinal() : null"
    override val codeBlockToFormat: String = "return \$N != null ? \$N.values()[\$N] : null"
}
