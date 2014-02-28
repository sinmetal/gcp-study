package org.sinmetal.gcp.study.controller.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sinmetal.gcp.study.controller.ApiTestCase;
import org.sinmetal.gcp.study.controller.search.DocumentController.IndexParamForSearchDocument;
import org.sinmetal.gcp.study.controller.search.DocumentController.PostDataForSearchDocument;
import org.sinmetal.gcp.study.controller.search.DocumentController.Type;
import org.sinmetal.gcp.study.test.AssertionUtil;
import org.sinmetal.gcp.study.util.JsonUtil;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link DocumentController}のテストケース。
 * @author sinmetal
 */
public class DocumentControllerTest extends ApiTestCase {

	/**
	 * @throws Exception
	 * @author sinmetal
	 */
	@Test
	public void testPost() throws Exception {
		PostDataForSearchDocument postData = new PostDataForSearchDocument();
		postData.id = "hogeId";
		{
			IndexParamForSearchDocument param = new IndexParamForSearchDocument();
			param.name = "name";
			param.value = "hoge";
			param.type = Type.Atom;
			postData.indexParams.add(param);
		}

		final String requestJson = JsonUtil.encode(postData);
		tester.request.setMethod("POST");
		tester.request.setContentType("application/json");
		tester.request.setInputStream(createInputStream(requestJson.getBytes()));
		tester.start("/search/document");
		assertThat(tester.getController(), instanceOf(DocumentController.class));
		assertThat(tester.response.getStatus(), is(200));
		assertThat(tester.response.getOutputAsString(),
				is("{\"results\":[{\"code\":\"OK\",\"message\":null}],\"ids\":[\"hogeId\"]}"));

		Document document = DocumentController.getIndex().get(postData.id);
		assertThat(document, notNullValue());

		List<Field> fields = buildFields(postData.indexParams);
		AssertionUtil.assertFtsDocument(document, fields);
	}

	List<Field> buildFields(List<IndexParamForSearchDocument> data) throws ParseException {
		List<Field> fields = new ArrayList<>();
		for (IndexParamForSearchDocument param : data) {
			Field.newBuilder().setName(param.name);
			switch (param.type) {
				case Atom:
					fields.add(Field.newBuilder().setName(param.name).setAtom(param.value).build());
					break;
				case Text:
					fields.add(Field.newBuilder().setName(param.name).setText(param.value).build());
					break;
				case HTML:
					fields.add(Field.newBuilder().setName(param.name).setHTML(param.value).build());
					break;
				case Number:
					fields.add(Field.newBuilder().setName(param.name)
						.setNumber(Double.parseDouble(param.value)).build());
					break;
				case Date:
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					fields.add(Field.newBuilder().setName(param.name)
						.setDate(dateFormat.parse(param.value)).build());
					break;
				case Geopoint:
					// TODO Geopoint
					break;
				default:
					throw new IllegalArgumentException("unknown type = " + param.type);
			}
		}
		return fields;
	}
}
