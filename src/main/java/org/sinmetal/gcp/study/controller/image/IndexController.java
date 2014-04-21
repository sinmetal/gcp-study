package org.sinmetal.gcp.study.controller.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

/**
 * FileUpload
 * @author sinmetal
 */
public class IndexController extends Controller {

	static Logger logger = Logger.getLogger(IndexController.class.getSimpleName());


	@Override
	protected Navigation run() throws Exception {
		if (isPost()) {
			FileItem formFile = requestScope("formFile");

			File file = new File(formFile.getFileName());
			try {
				new FileOutputStream(file).write(formFile.getData());
			} catch (Throwable e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
			return null;
		} else if (isGet()) {
			String filePath = request.getParameter("filepath");
			File file = new File(filePath);
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				copy(fileInputStream, response.getOutputStream());
				response.setContentType("octet-stream");
			} catch (Throwable e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
			return null;
		} else {
			return null;
		}

	}

	private void copy(InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
		} finally {
			input.close();
			output.close();
		}
	}

}
