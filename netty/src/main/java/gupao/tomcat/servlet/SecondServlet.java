package gupao.tomcat.servlet;


import gupao.tomcat.http.GPRequest;
import gupao.tomcat.http.GPResponse;
import gupao.tomcat.http.GPServlet;

public class SecondServlet extends GPServlet {
	@Override
	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}
	@Override
	public void doPost(GPRequest request, GPResponse response) throws Exception {
		response.write("This is Second Serlvet");
	}

}
