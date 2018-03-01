package com.jhonatansouza.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.jhonatansouza.exception.MapsException;
import com.jhonatansouza.pojo.MapPojo;
import com.jhonatansouza.pojo.StaticMap;

/**
 * Servlet implementation class Maps
 */
@WebServlet("/Maps")
public class Maps extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Maps() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GoogleMapsClient mp = new GoogleMapsClient();
		MapPojo m = new MapPojo();
		m.setOrigin(request.getParameter("origin"));
		m.setDestination(request.getParameter("init"));
		StaticMap stmp;
		response.setContentType("application/json;");
		response.setCharacterEncoding("UTF-8");
		
		try {
			stmp = mp.getMap(m);
			JSONObject obj = new JSONObject(stmp);
			response.getWriter().write(obj.toString());
		} catch (MapsException e) {
			response.setStatus(500);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
