/*
 * Copyright 2021 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.infrastructure.json.types;

import java.math.BigInteger;
import java.util.function.Function;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import tech.pegasys.teku.infrastructure.bytes.Bytes4;
import tech.pegasys.teku.infrastructure.http.HttpErrorResponse;
import tech.pegasys.teku.infrastructure.json.types.StringBasedPrimitiveTypeDefinition.StringTypeBuilder;
import tech.pegasys.teku.infrastructure.unsigned.UInt64;

public class CoreTypes {
  public static final StringValueTypeDefinition<String> STRING_TYPE = stringBuilder().build();

  public static final StringValueTypeDefinition<Boolean> BOOLEAN_TYPE = new BooleanTypeDefinition();

  public static final StringValueTypeDefinition<Byte> BYTE_TYPE = new ByteTypeDefinition();

  public static final StringValueTypeDefinition<Bytes32> BYTES32_TYPE =
      DeserializableTypeDefinition.string(Bytes32.class)
          .formatter(Bytes32::toHexString)
          .parser(Bytes32::fromHexString)
          .example("0xcf8e0d4e9587369b2301d0790347320302cc0943d5a1884560367e8208d920f2")
          .description("Bytes32 hexadecimal")
          .format("byte")
          .build();

  public static final DeserializableTypeDefinition<Bytes4> BYTES4_TYPE =
      DeserializableTypeDefinition.string(Bytes4.class)
          .formatter(Bytes4::toHexString)
          .parser(Bytes4::fromHexString)
          .example("0xcf8e0d4e")
          .description("Bytes4 hexadecimal")
          .format("byte")
          .build();

  public static final StringValueTypeDefinition<UInt64> UINT64_TYPE =
      DeserializableTypeDefinition.string(UInt64.class)
          .formatter(UInt64::toString)
          .parser(UInt64::valueOf)
          .example("1")
          .description("unsigned 64 bit integer")
          .format("uint64")
          .build();

  public static final DeserializableTypeDefinition<UInt256> UINT256_TYPE =
      DeserializableTypeDefinition.string(UInt256.class)
          .formatter(value -> value.toBigInteger().toString(10))
          .parser(value -> UInt256.valueOf(new BigInteger(value, 10)))
          .example("1")
          .description("unsigned 256 bit integer")
          .format("uint256")
          .build();

  public static final StringValueTypeDefinition<Integer> INTEGER_TYPE = new IntegerTypeDefinition();

  public static final SerializableTypeDefinition<HttpErrorResponse> HTTP_ERROR_RESPONSE_TYPE =
      SerializableTypeDefinition.object(HttpErrorResponse.class)
          .name("HttpErrorResponse")
          .withField("status", INTEGER_TYPE, HttpErrorResponse::getStatus)
          .withField("message", STRING_TYPE, HttpErrorResponse::getMessage)
          .build();

  public static DeserializableTypeDefinition<String> string(final String description) {
    return stringBuilder().description(description).build();
  }

  public static StringValueTypeDefinition<String> string(
      final String description, final String example) {
    return stringBuilder().description(description).example(example).build();
  }

  private static StringTypeBuilder<String> stringBuilder() {
    return DeserializableTypeDefinition.string(String.class)
        .formatter(Function.identity())
        .parser(Function.identity());
  }
}
