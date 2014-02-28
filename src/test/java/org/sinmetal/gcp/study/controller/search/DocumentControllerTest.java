package org.sinmetal.gcp.study.controller.search;

import java.util.List;

import org.junit.Test;
import org.sinmetal.gcp.study.controller.ApiTestCase;
import org.sinmetal.gcp.study.controller.search.DocumentController.IndexParamForSearchDocument;
import org.sinmetal.gcp.study.controller.search.DocumentController.PostDataForSearchDocument;
import org.sinmetal.gcp.study.service.SearchService;
import org.sinmetal.gcp.study.service.SearchService.Type;
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
	public void testGet() throws Exception {
		PostDataForSearchDocument postData = new PostDataForSearchDocument();
		postData.id = "hogeId";
		{
			IndexParamForSearchDocument param = new IndexParamForSearchDocument();
			param.name = "name";
			param.value = "hoge";
			param.type = Type.Atom;
			postData.indexParams.add(param);
		}
		SearchService.addToIndex(postData);

		tester.request.setMethod("GET");
		tester.start("/search/document");
		assertThat(tester.getController(), instanceOf(DocumentController.class));
		assertThat(tester.response.getStatus(), is(200));
	}

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

		Document document = SearchService.getIndex().get(postData.id);
		assertThat(document, notNullValue());

		List<Field> fields = SearchService.buildFields(postData.indexParams);
		AssertionUtil.assertFtsDocument(document, fields);
	}
}
