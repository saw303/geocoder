package ch.silviowangler.addresses.geocoder.client;

import ch.silviowangler.addresses.geocoder.api.LocationVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Silvio Wangler
 */
public class Client {

	public static final int OK = 200;

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ApiException {

		if (args.length == 0) {
			System.err.println("You need to provide an API key");
			System.exit(-1);
		}

		String apiKey = args[0];
		GeoApiContext context = new GeoApiContext.Builder()
				.apiKey(apiKey)
				.build();

		String baseEndPoint = "http://api.schaltzentrale.ch:9090/v1";

		if (args.length > 1) {
			baseEndPoint = args[1];
		}

		Gson gson = new GsonBuilder().create();
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest readData = HttpRequest.newBuilder()
				.uri(new URI(baseEndPoint + "/location/next"))
				.GET()
				.build();

		HttpResponse<String> response = client.send(readData, HttpResponse.BodyHandler.asString());

		while (response.statusCode() == OK) {
			LocationVO location = gson.fromJson(response.body(), LocationVO.class);

			final String address = String.format("%s %s %s %s", location.getStreet(), location.getHousenumber(), location.getZip(), location.getCity());

			System.out.println("Processing address " + address);

			GeocodingResult[] results = GeocodingApi.geocode(context, address).await();

			String result = gson.toJson(results);

			HttpRequest storeData = HttpRequest.newBuilder()
					.uri(new URI(baseEndPoint + "/location/" + location.getId().toString()))
					.POST(HttpRequest.BodyProcessor.fromString(result))
					.header("Content-Type", "application/json;charset=UTF-8")
					.build();

			HttpResponse<String> storeResponse = client.send(storeData, HttpResponse.BodyHandler.asString());

			if (storeResponse.statusCode() == OK) {
				System.out.println("Address successfully updated");
			} else {
				System.err.println(String.format("Could not update address (status %s, address: %s)", response.statusCode(), location));
				System.exit(-4);
			}
			response = client.send(readData, HttpResponse.BodyHandler.asString());
		}

		if (response.statusCode() != OK) {
			System.err.println("Something went wrong " + response.statusCode());
		}
	}
}
