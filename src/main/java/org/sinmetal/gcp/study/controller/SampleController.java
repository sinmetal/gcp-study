package org.sinmetal.gcp.study.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * Sample
 * @author sinmetal
 */
public class SampleController extends Controller {

	static Logger logger = Logger.getLogger(SampleController.class.getSimpleName());


	@Override
	protected Navigation run() throws Exception {
		List<File> files = getFiles();
		for (File file : files) {
			try {
				new FileOutputStream(file).write("Hello Managed VMs.".getBytes("utf-8"));
			} catch (Throwable e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}

		response.setContentType("text/plain");
		response.getWriter().append("done.");
		response.flushBuffer();
		return null;
	}

	List<File> getFiles() {
		List<File> files = new ArrayList<>();
		files.add(new File("/tmp/hello.txt"));
		files.add(new File("~/hello.txt"));
		return files;
	}
}
