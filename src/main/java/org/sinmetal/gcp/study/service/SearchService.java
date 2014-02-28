package org.sinmetal.gcp.study.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.sinmetal.gcp.study.controller.search.DocumentController.IndexParamForSearchDocument;
import org.sinmetal.gcp.study.controller.search.DocumentController.PostDataForSearchDocument;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.SearchServiceFactory;

/**
 * SearchAPIに関するService
 * @author sinmetal
 */
public class SearchService {

	/**
	 * @author sinmetal
	 */
	public enum Type {
		/** */
		Atom,
		/** */
		Text,
		/** */
		HTML,
		/** */
		Number,
		/** */
		Date,
		/** */
		Geopoint
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
	 * @return document
	 * @author sinmetal
	 */
	public static GetResponse<Document> getAll() {
		GetRequest request = GetRequest.newBuilder().build();
		return SearchService.getIndex().getRange(request);
	}

	/**
	 * @return document
	 * @author sinmetal
	 */
	public static GetResponse<Document> getAllId() {
		GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();
		return SearchService.getIndex().getRange(request);
	}

	/**
	 * @param postData
	 * @return put response
	 * @throws ParseException
	 * @author sinmetal
	 */
	public static PutResponse addToIndex(PostDataForSearchDocument postData) throws ParseException {
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
	 * @param data
	 * @return field list
	 * @throws ParseException
	 * @author sinmetal
	 */
	public static List<Field> buildFields(List<IndexParamForSearchDocument> data)
			throws ParseException {
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
