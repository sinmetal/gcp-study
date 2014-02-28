package org.sinmetal.gcp.study.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Assertionに関するUtil
 * @author sinmetal
 */
public class AssertionUtil {

	/**
	 * setの中にvaluesの値しかないことを確認するassert
	 * @param set
	 * @param values
	 * @author sinmetal
	 */
	@SuppressWarnings("unchecked")
	public static <T>void assertSet(Set<T> set, T... values) {
		assertThat("Setがnullではない", set, notNullValue());
		assertThat("valuesがnullではない", values, notNullValue());
		assertThat(
				String.format("Setとvaluesのsizeが一致している。 set.size = %d, values.length = %d",
						set.size(), values.length), set.size(), is(values.length));
		for (T value : values) {
			assertThat(value + "が存在する", set.contains(value), is(true));
		}
	}

	/**
	 * listの中にvaluesの値しか無いことを確認するassert
	 * @param list
	 * @param values
	 * @author sinmetal
	 */
	@SuppressWarnings("unchecked")
	public static <T>void assertList(List<T> list, T... values) {
		assertThat("listがnullではない", list, notNullValue());
		assertThat("valuesがnullではない", values, notNullValue());
		assertThat(
				String.format("Listとvaluesのsizeが一致している。 list.size = %d, values.length = %d",
						list.size(), values.length), list.size(), is(values.length));
		for (T value : values) {
			assertThat(value + "が存在する", list.contains(value), is(true));
		}
	}

	/**
	 * setの中にvaluesの値が無いことを確認するassert
	 * @param set
	 * @param values
	 * @author sinmetal
	 */
	@SuppressWarnings("unchecked")
	public static <T>void assertSetNotFound(Set<T> set, T... values) {
		assertThat("Setがnullではない", set, notNullValue());
		assertThat("valuesがnullではない", values, notNullValue());
		for (T value : values) {
			assertThat(value + "が存在してしまった", set.contains(value), is(false));
		}
	}

	/**
	 * mapとvalueの中身が一致することを確認するassert
	 * @param map
	 * @param value
	 * @author sinmetal
	 */
	public static <K, V>void assertMap(Map<K, V> map, Map<K, V> value) {
		assertThat("Mapがnullではない", map, notNullValue());
		assertThat("valueがnullではない", value, notNullValue());
		assertThat(String.format("Mapとvalueのsizeが一致している。 map.size = %d, value.length = %d",
				map.size(), value.size()), map.size(), is(value.size()));
		for (Entry<K, V> entry : value.entrySet()) {
			assertThat(entry.getKey() + "が存在する", map.containsKey(entry.getKey()), is(true));
			assertThat(
					String.format(entry.getKey() + "の値が一致する。 map = %s. value = %s",
							map.get(entry.getKey()), entry.getValue()), map.get(entry.getKey()),
					is(entry.getValue()));
		}
	}

	/**
	 * Full Text Search assert
	 * @param document
	 * @param fields 想定しているField一覧
	 * @author sinmetal
	 */
	public static void assertFtsDocument(Document document, List<Field> fields) {
		Map<String, Field> map = new HashMap<>();
		for (Field field : fields) {
			map.put(field.getName(), field);
		}

		int count = 0;
		for (Field field : document.getFields()) {
			assertThat("想定したFieldがある。 field name = " + field.getName(),
					map.containsKey(field.getName()), is(true));

			Field value = map.get(field.getName());

			assertThat("FieldのTypeが一致する", field.getType(), is(value.getType()));
			switch (field.getType()) {
				case TEXT:
					assertThat("TextFieldの値が一致する", field.getText(), is(value.getText()));
					break;
				case HTML:
					assertThat("HtmlFieldの値が一致する", field.getHTML(), is(value.getHTML()));
					break;
				case ATOM:
					assertThat("AtomFieldの値が一致する", field.getAtom(), is(value.getAtom()));
					break;
				case DATE:
					assertThat("DateFieldの値が一致する", field.getDate(), is(value.getDate()));
					break;
				case NUMBER:
					assertThat("NumberFieldの値が一致する", field.getNumber(), is(value.getNumber()));
					break;
				case GEO_POINT:
					assertThat("GeoPointFieldの値が一致する", field.getDate(), is(value.getDate()));
					break;
				default:
					throw new IllegalArgumentException(String.format("Unknown field type given %s",
							field.getType()));

			}

			count++;
		}

		assertThat("Fieldの個数が一致する", count, is(fields.size()));
	}
}
