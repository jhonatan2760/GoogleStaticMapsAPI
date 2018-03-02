package com.jhonatansouza.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jhonatansouza.exception.MapsException;
import com.jhonatansouza.pojo.InstructionsPojo;
import com.jhonatansouza.pojo.MapPojo;
import com.jhonatansouza.pojo.RoutePojo;
import com.jhonatansouza.pojo.StaticMap;

public class GoogleMapsClient {

	private final String url = "https://maps.googleapis.com/maps/api/directions/json";
	private final String maps = "https://maps.googleapis.com/maps/api/staticmap?size=600x300&maptype=roadmap";
	private final String key = "";

	public GoogleMapsClient() {

	}

	public StaticMap getMap(MapPojo m) throws MapsException, IOException {

		StaticMap map = new StaticMap();
			InstructionsPojo p;
				p = loadInstructions(m);
				map.setInstructions(p.getInstructions());
				map.setImage(this.generateMap(p.getPolyLine()));
		return map;
	}

	private InstructionsPojo loadInstructions(MapPojo m) throws IOException {
		Map<String, String> params = new HashMap<>();
		List<RoutePojo> stepsSender = new ArrayList<>();
		params.put("origin", m.getOrigin());
		params.put("destination", m.getDestination());
		params.put("key", this.key);
		Document doc = this.retrieveDocument(url, params).get();
		JSONObject jsonobj = new JSONObject(doc.text());
		JSONArray arr = jsonobj.getJSONArray("routes");
		JSONArray steps = arr.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
		steps.forEach(ins -> {
			stepsSender.add(new RoutePojo(((JSONObject) ins).getString("html_instructions")));
		});
		InstructionsPojo p = new InstructionsPojo();
		JSONObject jsrec = arr.getJSONObject(0).getJSONObject("overview_polyline");
		String points = jsrec.getString("points");
		p.setInstructions(stepsSender);
		p.setPolyLine(points);
		return p;

	}

	private String generateMap(String lines) throws IOException {
		HashMap<String, String> params = new HashMap<>();
		params.put("path", "enc:".concat(lines));
		params.put("key", this.key);
		Response rr = this.retrieveDocument(this.maps, params).execute();
		return Base64.encodeBase64String(rr.bodyAsBytes());
	}

	private Connection retrieveDocument(String url, Map<String, String> params) throws IOException {
		return Jsoup.connect(url).data(params).ignoreContentType(true);
	}

	private void validateResult(StaticMap mp) throws MapsException {
		if (mp.getImage() == null | mp.getInstructions() == null)
			throw new MapsException("Falha ao receber mapa!");
	}
}
