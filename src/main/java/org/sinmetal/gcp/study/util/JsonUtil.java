package org.sinmetal.gcp.study.util;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Jsonに関するUtil
 * @author sinmetal
 */
public class JsonUtil {

	/**
	 * json encode
	 * @param obj
	 * @return json
	 * @author sinmetal
	 */
	public static String encode(Object obj) {
		try {
			return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * json encode <br>
	 * 渡されたwriterにjsonを書き込む
	 * @param writer
	 * @param obj
	 * @author sinmetal
	 */
	public static void encode(Writer writer, Object obj) {
		try {
			new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValue(writer,
					obj);
		} catch (IOException e) {
			throw new IllegalStateException();
		}
	}

	/**
	 * json decode
	 * @param json
	 * @param clazz
	 * @return clazz
	 * @author sinmetal
	 */
	public static <T>T decode(String json, Class<T> clazz) {
		try {
			return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.readValue(json, clazz);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * json decode
	 * @param json
	 * @param valueTypeRef 
	 * @return clazz
	 * @author sinmetal
	 */
	public static <T>T decode(String json, TypeReference<?> valueTypeRef) {
		try {
			return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.readValue(json, valueTypeRef);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
