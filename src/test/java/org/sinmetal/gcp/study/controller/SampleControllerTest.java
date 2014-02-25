package org.sinmetal.gcp.study.controller;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * {@link SampleController}のテストケース
 * @author sinmetal
 */
public class SampleControllerTest extends ControllerTestCase {

	/**
	 * test for sample
	 * @throws Exception
	 * @author sinmetal
	 */
	@Test
	public void testSample() throws Exception {
		tester.start("/sample");
		assertThat(tester.response.getStatus(), is(200));
	}

}
