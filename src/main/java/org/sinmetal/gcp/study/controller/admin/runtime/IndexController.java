package org.sinmetal.gcp.study.controller.admin.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletResponse;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.apphosting.api.ApiProxy;

/**
 * Command実行する
 * @author sinmetal
 */
public class IndexController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		String[] command = request.getParameterValues("command");

		ProcessBuilder pb = new ProcessBuilder();
		pb.command(command);
		Process process = pb.start();

		int ret = process.waitFor();
		System.out.println("戻り値：" + ret);

		Object object =
				ApiProxy.getCurrentEnvironment().getAttributes()
					.get("com.google.appengine.instance.id");
		response.getWriter().write("instance-id : " + object.toString());
		response.getWriter().write("\n");

		// 標準出力
		printInputStream(process.getInputStream(), response);
		// 標準エラー
		printInputStream(process.getErrorStream(), response);

		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.flushBuffer();
		return null;
	}

	static void printInputStream(InputStream is, HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				response.getWriter().write(line);
				response.getWriter().write("\n");
			}
		} finally {
			br.close();
		}
	}
}
