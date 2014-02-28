package org.sinmetal.gcp.study.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletInputStream;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.slim3.controller.ControllerConstants;
import org.slim3.controller.SimpleController;
import org.slim3.tester.ControllerTestCase;
import org.slim3.tester.TestEnvironment;

import com.google.appengine.api.backends.BackendService;
import com.google.appengine.repackaged.com.google.common.io.ByteStreams;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalFileServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;

/**
 * {@link TestCase}。
 * @author sinmetal
 */
public class ApiTestCase extends ControllerTestCase {

	LocalServiceTestHelper helper;


	@Override
	@Before
	public void setUp() throws Exception {
		LocalTaskQueueTestConfig tqConfig =
				new LocalTaskQueueTestConfig().setQueueXmlPath("src/main/webapp/WEB-INF/queue.xml");
		LocalDatastoreServiceTestConfig dsConfig =
				new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy();
		LocalSearchServiceTestConfig ssConfig =
				new LocalSearchServiceTestConfig().setPersistent(false);
		LocalFileServiceTestConfig fileConfig = new LocalFileServiceTestConfig();
		helper = new LocalServiceTestHelper(tqConfig, dsConfig, ssConfig, fileConfig);
		helper.setUp();

		Object listeners =
				ApiProxy.getCurrentEnvironment().getAttributes()
					.get("com.google.appengine.tools.development.request_end_listeners");

		super.setUp();

		// Slim3がUnitTestの時にAttributesを置き換えてしまうので、再セット
		ApiProxy.getCurrentEnvironment().getAttributes()
			.put("com.google.appengine.tools.development.request_end_listeners", listeners);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		helper.tearDown();
	}

	/**
	 * the constructor.
	 * @category constructor
	 */
	public ApiTestCase() {
		super();
		tester.servletContext.setInitParameter(ControllerConstants.ROOT_PACKAGE_KEY,
				"org.sinmetal.gcp.study");
	}

	/**
	 * テスト環境にログインユーザを設定する.
	 * @param email
	 * @author sinmetal
	 */
	public static void login(String email) {
		login(email, false);
	}

	/**
	 * テスト環境にログインユーザーを設定する。
	 *
	 * @param email
	 * @param isAdmin 管理者として扱うか
	 * @author sinmetal
	 */
	public static void login(String email, boolean isAdmin) {
		TestEnvironment env = (TestEnvironment) ApiProxy.getCurrentEnvironment();
		env.setEmail(email);
		env.setAdmin(isAdmin);
	}

	/**
	 * 現在のユーザーを管理者として扱います。
	 *
	 * @param isAdmin 管理者として扱うか
	 * @author sinmetal
	 */
	public static void setAdmin(boolean isAdmin) {
		TestEnvironment env = (TestEnvironment) ApiProxy.getCurrentEnvironment();
		env.setAdmin(isAdmin);
	}

	/**
	 * テスト環境にログインしているユーザをログアウトすする.
	 * @author sinmetal
	 */
	public static void logout() {
		TestEnvironment env = (TestEnvironment) ApiProxy.getCurrentEnvironment();
		env.setEmail(null);
	}

	/**
	 * {@link SimpleController}へJsonをPostするときに必要な{@link ServletInputStream}を作成する。
	 * <pre>
	 * tester.request.setMethod("POST");
	 * byte[] json =
	 *   IOUtils.toString(Test.class.getResourceAsStream("/test.json")).getBytes("utf-8");
	 * tester.request.setInputStream(<strong>ControllerTestUtil.createInputStream(json)</strong>);
	 * </pre>
	 * @param content
	 * @return ServletInputStream
	 * @author sinmetal
	 */
	public static ServletInputStream createInputStream(final byte[] content) {
		return new ServletInputStream() {

			ByteArrayInputStream in = new ByteArrayInputStream(content);


			@Override
			public int available() {
				return in.available();
			}

			@Override
			public int read() {
				return in.read();
			}

			@Override
			public int read(byte[] b, int off, int len) {
				return in.read(b, off, len);
			}
		};
	}

	/**
	 * {@link SimpleController}へJsonをPostするときに必要な{@link ServletInputStream}を作成する。
	 * <pre>
	 * tester.request.setMethod("POST");
	 * byte[] json =
	 *   IOUtils.toString(Test.class.getResourceAsStream("/test.json")).getBytes("utf-8");
	 * tester.request.setInputStream(<strong>ControllerTestUtil.createInputStream(json)</strong>);
	 * </pre>
	 * @param input
	 * @return ServletInputStream
	 * @author sinmetal
	 * @throws IOException
	 */
	public static ServletInputStream createInputStream(InputStream input) throws IOException {
		return createInputStream(ByteStreams.toByteArray(input));
	}

	/**
	 * リソースを取得する。
	 *
	 * @param filePath
	 * @return {@link InputStream}
	 * @author sinmetal
	 */
	public static InputStream getResource(String filePath) {
		return ApiTestCase.class.getResourceAsStream(filePath);
	}

	/**
	 * backendであるように見せかけます。
	 *
	 * @author sinmetal
	 */
	public static void setUpBackend() {
		Environment env = ApiProxy.getCurrentEnvironment();
		Map<String, Object> attr = env.getAttributes();
		if (attr == null) {
			throw new RuntimeException(
					"Local environment is corrupt (thread local attributes map is null)");
		}

		attr.put(BackendService.BACKEND_ID_ENV_ATTRIBUTE, "hoge");
	}

}
