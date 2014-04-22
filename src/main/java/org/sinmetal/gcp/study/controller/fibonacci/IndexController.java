package org.sinmetal.gcp.study.controller.fibonacci;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * フィボナッチ数列を無限に数え続ける
 * @author sinmetal
 */
public class IndexController extends Controller {

	static long value = 0;

	static long count = 0;


	@Override
	protected Navigation run() throws Exception {
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");

		if (isPost()) {
			String parameter = request.getParameter("length");
			int length = Integer.valueOf(parameter);
			if (length == 0) {
				value = 0;
				count = 0;
				response.getWriter().write("count : " + String.valueOf(count));
				response.getWriter().write("\n");
				response.getWriter().write("value : " + String.valueOf(value));
				return null;
			}
			for (int i = 1; i < length; i++) {
				count = i;
				value = fib(i);
				Thread.sleep(10000);
			}
			response.getWriter().write("count : " + String.valueOf(count));
			response.getWriter().write("\n");
			response.getWriter().write("value : " + String.valueOf(value));
			return null;
		}
		if (isGet()) {
			response.getWriter().write("count : " + String.valueOf(count));
			response.getWriter().write("\n");
			response.getWriter().write("value : " + String.valueOf(value));
			return null;
		}
		return null;
	}

	static long fib(int n) {
		// 0以下の整数番目のフィボナッチ数は定義しないという意味で 0 を返す
		if (n <= 0)
			return (0);

		// fib(1) = 1, fib(2) = 1
		if (n == 1)
			return (1);
		if (n == 2)
			return (1);

		// fib(3) 以上は再帰的に定義
		return (fib(n - 2) + fib(n - 1));
	}

}
