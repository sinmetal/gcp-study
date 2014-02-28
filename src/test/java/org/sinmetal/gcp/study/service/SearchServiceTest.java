package org.sinmetal.gcp.study.service;

import java.util.List;

import org.junit.Test;
import org.sinmetal.gcp.study.controller.ApiTestCase;
import org.sinmetal.gcp.study.controller.search.DocumentController.IndexParamForSearchDocument;
import org.sinmetal.gcp.study.controller.search.DocumentController.PostDataForSearchDocument;
import org.sinmetal.gcp.study.service.SearchService.Type;
import org.sinmetal.gcp.study.test.AssertionUtil;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GetResponse;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * {@link SearchService}
 * @author sinmetal
 */
public class SearchServiceTest extends ApiTestCase {

	/**
	 * @throws Exception
	 * @author sinmetal
	 */
	@Test
	public void testGetAll() throws Exception {
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

		GetResponse<Document> all = SearchService.getAll();
		List<Document> results = all.getResults();
		assertThat(results.size(), is(1));

		List<Field> fields = SearchService.buildFields(postData.indexParams);
		AssertionUtil.assertFtsDocument(results.get(0), fields);
	}
}
