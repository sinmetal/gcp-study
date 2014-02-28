package org.sinmetal.gcp.study.controller.search;

import java.util.ArrayList;
import java.util.List;

import org.sinmetal.gcp.study.service.SearchService;
import org.sinmetal.gcp.study.service.SearchService.Type;
import org.sinmetal.gcp.study.util.JsonUtil;
import org.sinmetal.gcp.study.util.RequestUtil;
import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.PutResponse;

/**
 * Search API Sample
 * https://developers.google.com/appengine/docs/java/search/
 * @author sinmetal
 */
public class DocumentController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		if (isGet()) {
			doGet();
			return null;
		}
		if (isPost()) {
			doPost();
			return null;
		}
		if (isDelete()) {
			throw new UnsupportedOperationException("unknown method =" + request.getMethod());
		}

		throw new UnsupportedOperationException("unknown method =" + request.getMethod());
	}

	void doGet() throws Exception {
		GetResponse<Document> documents = SearchService.getAll();

		response.setHeader("Content-Type", "application/json");
		response.getWriter().write(JsonUtil.encode(documents));
		response.getWriter().flush();
	}

	void doPost() throws Exception {
		final String requestBody = RequestUtil.getBody(request);
		PostDataForSearchDocument postData =
				JsonUtil.decode(requestBody, PostDataForSearchDocument.class);
		PutResponse putResponse = SearchService.addToIndex(postData);
		response.setHeader("Content-Type", "application/json");
		response.getWriter().write(JsonUtil.encode(putResponse));
		response.getWriter().flush();
	}


	/**
	 * @author sinmetal
	 */
	public static class PostDataForSearchDocument {

		public String id;

		public List<IndexParamForSearchDocument> indexParams = new ArrayList<>();
	}

	/**
	 * @author sinmetal
	 */
	public static class IndexParamForSearchDocument {

		public String name;

		public Type type;

		public String value;
	}
}
