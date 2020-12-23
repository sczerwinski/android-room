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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@DisplayName("Tests for generated Room type converters for enum as ordinal")
class EnumAsOrdinalConverterTest {

    @ParameterizedTest(name = "{index}: {0} -> {1}")
    @MethodSource(value = ["testData"])
    @DisplayName(value = "GIVEN enum value, WHEN fromEnum, THEN return ordinal value")
    fun fromEnum(enumValue: EnumAsOrdinal?, ordinalValue: Int?) {
        val result = EnumAsOrdinalConverter().fromEnumAsOrdinal(enumValue)

        assertEquals(ordinalValue, result)
    }

    @ParameterizedTest(name = "{index}: {1} -> {0}")
    @MethodSource(value = ["testData"])
    @DisplayName(value = "GIVEN ordinal value, WHEN toEnum, THEN return enum value")
    fun toEnum(enumValue: EnumAsOrdinal?, ordinalValue: Int?) {
        val result = EnumAsOrdinalConverter().toEnumAsOrdinal(ordinalValue)

        assertEquals(enumValue, result)
    }

    companion object {
        @JvmStatic
        fun testData(): List<Arguments> = listOf(
            Arguments.of(EnumAsOrdinal.FOO, 0),
            Arguments.of(EnumAsOrdinal.BAR, 1),
            Arguments.of(null, null),
        )
    }
}
