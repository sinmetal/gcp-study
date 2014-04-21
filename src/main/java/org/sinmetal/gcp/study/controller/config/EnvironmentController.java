package org.sinmetal.gcp.study.controller.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sinmetal.gcp.study.util.JsonUtil;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.apphosting.api.ApiProxy;

/**
 * requestの情報
 * @author sinmetal
 */
public class EnvironmentController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("header", getHeaders(request));
		responseMap.put("environment", ApiProxy.getCurrentEnvironment().getAttributes());

		JsonUtil.encode(response.getWriter(), responseMap);
		return null;
	}

	/**
	 * Header情報をログに出力する
	 * @param request
	 * @return header map
	 * @author sinmetal 
	 */
	Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> res = new HashMap<>();
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String key = headerNames.nextElement();
			res.put(key, request.getHeader(key));
		}
		return res;
	}

}
