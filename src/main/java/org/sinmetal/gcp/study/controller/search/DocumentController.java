package org.sinmetal.gcp.study.controller.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.sinmetal.gcp.study.util.JsonUtil;
import org.sinmetal.gcp.study.util.RequestUtil;
import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.SearchServiceFactory;

/**
 * Search API Sample
 * https://developers.google.com/appengine/docs/java/search/
 * @author sinmetal
 */
public class DocumentController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		if (isGet()) {
			throw new UnsupportedOperationException("unknown method =" + request.getMethod());
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

	void doPost() throws Exception {
		final String requestBody = RequestUtil.getBody(request);
		PostDataForSearchDocument postData =
				JsonUtil.decode(requestBody, PostDataForSearchDocument.class);
		PutResponse putResponse = addToIndex(postData);
		response.setHeader("Content-Type", "application/json");
		response.getWriter().write(JsonUtil.encode(putResponse));
		response.getWriter().flush();
	}

	static PutResponse addToIndex(PostDataForSearchDocument postData) throws ParseException {
		// @formatter:off
		Builder builder = Document.newBuilder()
			.setId(postData.id)
			.setLocale(Locale.JAPAN);
		// @formatter:on
		for (IndexParamForSearchDocument param : postData.indexParams) {
			switch (param.type) {
				case Atom:
					builder.addField(Field.newBuilder().setName(param.name).setAtom(param.value));
					break;
				case Text:
					builder.addField(Field.newBuilder().setName(param.name).setText(param.value));
					break;
				case HTML:
					builder.addField(Field.newBuilder().setName(param.name).setHTML(param.value));
					break;
				case Number:
					builder.addField(Field.newBuilder().setName(param.name)
						.setNumber(Double.parseDouble(param.value)));
					break;
				case Date:
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					builder.addField(Field.newBuilder().setName(param.name)
						.setDate(dateFormat.parse(param.value)));
					break;
				case Geopoint:
					// TODO Geopoint
					break;
				default:
					throw new IllegalArgumentException("unknown type = " + param.type);
			}
		}
		return getIndex().put(builder.build());
	}

	/**
	 * @return {@link Index} for document of Sample.
	 * @author sinmetal
	 */
	public static Index getIndex() {
		return SearchServiceFactory.getSearchService().getIndex(
				IndexSpec.newBuilder().setName("Sample").build());
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

	/**
	 * @author sinmetal
	 */
	public enum Type {
		Atom, Text, HTML, Number, Date, Geopoint
	}
}
