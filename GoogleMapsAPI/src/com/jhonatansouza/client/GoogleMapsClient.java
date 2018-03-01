package com.jhonatansouza.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jhonatansouza.exception.MapsException;
import com.jhonatansouza.pojo.MapPojo;
import com.jhonatansouza.pojo.RoutePojo;
import com.jhonatansouza.pojo.StaticMap;

public class GoogleMapsClient {

	private final String url = "https://maps.googleapis.com/maps/api/directions/json";
	private final String maps = "https://maps.googleapis.com/maps/api/staticmap?size=600x300&maptype=roadmap";
	private final String key = "AIzaSyD3ugNay_TZOIlxHCcKHRvTrlWr5rhSJP0";
	private Document doc;
	public GoogleMapsClient() {
		
	}
	
	public StaticMap getMap(MapPojo m) throws MapsException {

		StaticMap map = new StaticMap();
		try {
			this.loadMap(m, map);
		} catch (IOException e) {
			throw new MapsException("Falha ao buscar mapa!");
		}
		return map;
	}
	
	private void loadMap(MapPojo m, StaticMap mp) throws IOException {
		Map<String, String> params = new HashMap<>();
		List<RoutePojo> stepsSender = new ArrayList<>();
		params.put("origin", m.getOrigin() );
		params.put("destination", m.getDestination());
		params.put("key", this.key);
		this.doc = Jsoup.connect(this.url).data(params).ignoreContentType(true).get();
			JSONObject jsonobj = new JSONObject(doc.text());
			JSONArray arr = jsonobj.getJSONArray("routes");
			JSONArray steps = arr.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
			steps.forEach( ins -> {
				stepsSender.add( new RoutePojo(((JSONObject) ins ).getString("html_instructions")));
			});
			mp.setInstructions(stepsSender);
			JSONObject jsrec = arr.getJSONObject(0).getJSONObject("overview_polyline");
			String points = jsrec.getString("points");
			
		Response r = Jsoup.connect(this.maps).ignoreContentType(true).data("key", this.key).data("path", "enc:"+points).execute();
		mp.setImage(Base64.encodeBase64String(r.bodyAsBytes()));
	}
	
	
}
