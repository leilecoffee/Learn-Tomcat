package ex01;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class ServletProcessor1 {

	@SuppressWarnings("rawtypes")
	public void processor(Request request, Response response) {
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(Constants.WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();

			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
		Class myClass = null;
		try {
			myClass = loader.loadClass(servletName);
		}catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		
		Servlet servlet = null;
		try {
			servlet = (Servlet) myClass.newInstance();
			servlet.service(request, response);
		} catch (InstantiationException | IllegalAccessException | ServletException | IOException e) {
			System.out.println(e.toString());
		}
		

	}
}
