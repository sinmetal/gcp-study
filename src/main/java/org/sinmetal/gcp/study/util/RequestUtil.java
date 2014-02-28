package org.sinmetal.gcp.study.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;

/**
 * {@link HttpServletRequest} に関するユーティリティ。
 * @author sinmetal
 */
public class RequestUtil {

	/**
	 * 入力必須チェック付きでパラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static String requiredString(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (StringUtil.isEmpty(value)) {
			throw new IllegalArgumentException(name + " is required.");
		}
		return value;
	}

	/**
	 * 入力必須チェック付きでパラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static List<String> requiredStringList(HttpServletRequest request, String name) {
		String[] values = request.getParameterValues(name + "[]");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException(name + " is required.");
		}
		return Arrays.asList(values);
	}

	/**
	 * パラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static String getString(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

	/**
	 * パラメータを取得する。<br>
	 * もし値がない場合でも空のリストを返す。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static List<String> getStringList(HttpServletRequest request, String name) {
		String[] values = request.getParameterValues(name + "[]");
		if (values == null || values.length == 0) {
			return new ArrayList<String>();
		}
		return Arrays.asList(values);
	}

	/**
	 * 入力必須チェック付きでパラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static long requiredLong(HttpServletRequest request, String name) {
		String valueStr = requiredString(request, name);
		return Long.valueOf(valueStr);
	}

	/**
	 * 入力必須チェック付きでパラメータを取得する。 
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static List<Long> requiredLongList(HttpServletRequest request, String name) {
		List<Long> longValueList = new ArrayList<Long>();

		List<String> valueList = requiredStringList(request, name);
		for (String value : valueList) {
			longValueList.add(Long.valueOf(value));
		}
		return longValueList;
	}

	/**
	 * 入力必須チェック付きでパラメータを取得する。 
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static List<Double> requiredDoubleList(HttpServletRequest request, String name) {
		List<Double> doubleValueList = new ArrayList<Double>();

		List<String> valueList = requiredStringList(request, name);
		for (String value : valueList) {
			doubleValueList.add(Double.valueOf(value));
		}
		return doubleValueList;
	}

	/**
	 * 入力必須チェック付きでパラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static boolean requiredBoolean(HttpServletRequest request, String name) {
		String valueStr = requiredString(request, name);
		return Boolean.valueOf(valueStr);
	}

	/**
	 * 入力必須チェック付きでパラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static List<Boolean> requiredBooleanList(HttpServletRequest request, String name) {
		String[] values = request.getParameterValues(name + "[]");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException(name + " is required.");
		}
		List<Boolean> resultList = new ArrayList<>();
		for (String value : values) {
			resultList.add(Boolean.valueOf(value));
		}
		return resultList;
	}

	/**
	 * パラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static Long getLong(HttpServletRequest request, String name) {
		String value = getString(request, name);
		if (value == null) {
			return null;
		}
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("name=" + name + " value=" + value
					+ " not match as LongList.", e);
		}
	}

	/**
	 * パラメータを取得する。<br>
	 * もし値がない場合でも空のリストを返す。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static List<Long> getLongList(HttpServletRequest request, String name) {
		List<Long> longValueList = new ArrayList<Long>();

		List<String> valueList = getStringList(request, name);
		if (valueList.size() == 0) {
			return longValueList;
		}
		for (String value : valueList) {
			try {
				longValueList.add(Long.valueOf(value));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("name=" + name + " value=" + value
						+ " not match as LongList.", e);
			}
		}
		return longValueList;
	}

	/**
	 * パラメータを取得する。
	 * @param request
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static Boolean getBoolean(HttpServletRequest request, String name) {
		String value = getString(request, name);
		if (value == null) {
			return null;
		}
		return Boolean.valueOf(value);
	}

	/**
	 * 入力必須チェック付きでEnumでパラメータを取得する。
	 * @param request
	 * @param enumClass
	 * @param name
	 * @return パラメータ
	 * @author sinmetal
	 */
	public static <T extends Enum<T>>T requiredEnum(HttpServletRequest request, Class<T> enumClass,
			String name) {
		String value = requiredString(request, name);
		try {
			return Enum.valueOf(enumClass, value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("name=" + name + " value=" + value
					+ " not match to " + enumClass.getSimpleName(), e);
		}
	}

	/**
	 * 入力必須チェック付きで {@link Key} を取得する。
	 * @param request
	 * @param name
	 * @return {@link Key}
	 * @author sinmetal
	 */
	public static Key requiredKey(HttpServletRequest request, String name) {
		String value = requiredString(request, name);
		try {
			return Datastore.stringToKey(value);
		} catch (Exception e) {
			throw new IllegalArgumentException("name=" + name + " value=" + value
					+ " can't convert to Key", e);
		}
	}

	/**
	 * 入力必須チェック付きで {@link Key} を取得する。
	 * @param request
	 * @param name
	 * @return {@link Key}
	 * @author sinmetal
	 */
	public static List<Key> requiredKeyList(HttpServletRequest request, String name) {
		List<String> values = requiredStringList(request, name);
		try {
			List<Key> keyList = new ArrayList<>();
			for (String value : values) {
				keyList.add(Datastore.stringToKey(value));
			}
			return keyList;
		} catch (Exception e) {
			throw new IllegalArgumentException("name=" + name + " values=" + values
					+ " can't convert to Key", e);
		}
	}

	/**
	 * {@link Key} を取得する。
	 * @param request
	 * @param name
	 * @return {@link Key}
	 * @author sinmetal
	 */
	public static Key getKey(HttpServletRequest request, String name) {
		String keystr = getString(request, name);
		if (StringUtil.isEmpty(keystr)) {
			return null;
		}
		return Datastore.stringToKey(keystr);
	}

	/**
	 * {@link Key} を取得する。
	 * @param request
	 * @param name
	 * @return {@link Key}
	 * @author sinmetal
	 */
	public static List<Key> getKeyList(HttpServletRequest request, String name) {
		List<String> values = getStringList(request, name);
		if (values == null) {
			return null;
		} else if (values.size() == 0) {
			return new ArrayList<>();
		}
		try {
			List<Key> keyList = new ArrayList<>();
			for (String value : values) {
				keyList.add(Datastore.stringToKey(value));
			}
			return keyList;
		} catch (Exception e) {
			throw new IllegalArgumentException("name=" + name + " values=" + values
					+ " can't convert to Key", e);
		}
	}

	/**
	 * request bodyを取得する
	 * @param request
	 * @return body
	 * @throws IOException
	 * @author sinmetal
	 */
	public static String getBody(HttpServletRequest request) throws IOException {
		final StringBuilder builder = new StringBuilder();

		try (InputStream inputStream = request.getInputStream()) {
			if (inputStream == null) {
				return "";
			}
			try (InputStreamReader inputStreamReader =
					new InputStreamReader(request.getInputStream(), "utf-8");
					BufferedReader reader = new BufferedReader(inputStreamReader)) {
				char[] buffer = new char[256];
				int readSize = -1;
				while ((readSize = reader.read(buffer)) > 0) {
					builder.append(buffer, 0, readSize);
				}
			}
		}
		return builder.toString();
	}
}
