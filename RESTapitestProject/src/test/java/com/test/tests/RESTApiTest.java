package com.test.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.relevantcodes.extentreports.LogStatus;
import com.test.utility.TestBase;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class RESTApiTest extends TestBase {

	@Test
	public void test_get_statusCodeTest() {
		logger = extent.startTest("REST API get test1 started");

		RestAssured.baseURI = "http://services.groupkt.com/country/get";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/all");

		System.out.println(" response = " + response.body().toString());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.log(LogStatus.PASS, "Rest api get method test passed");
	}

    @Test
	public void test_get_resultStringTest() {
		logger = extent.startTest("REST API test resultStringTest started");

		RestAssured.baseURI = "http://services.groupkt.com/country/get";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/all");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		String bodyAsString = body.asString();
		System.out.println("body String   from result   @@ = " + bodyAsString);
		Assert.assertEquals(bodyAsString.contains("249"), true, "expected result string matched");

		logger.log(LogStatus.PASS, "Rest api get method with result string test passed");

	}

	@Test
	public void test_get_jsonPathEvaluatorTest() {

		logger = extent.startTest("REST API test JSON path evluator started");

		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
		String city = jsonPathEvaluator.get("City");

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + city);

		// Validate the response
		Assert.assertEquals(city, "Hyderabad", "Correct city name received in the Response");

		logger.log(LogStatus.PASS, "jsonPathEvaluator test passed");
	}

	@Test
	public void test_get_parseJsonArrayTest() throws JSONException, ParseException {
		logger = extent.startTest("REST API test parseJSONArrayTest started");

		JSONObject jSONResponseBody = null;
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification httpRequest = builder.build();
		
		RestAssured.baseURI = "http://services.groupkt.com/country/get";

		httpRequest = RestAssured.given();
		Response response = httpRequest.get("/all");

		jSONResponseBody = new JSONObject(response.body().asString());		
		JSONObject allKeys = new JSONObject(jSONResponseBody.getString("RestResponse"));			
		String resultKey = allKeys.getString("result");		
		JSONArray ja = new JSONArray(resultKey);		
		JSONObject firstArrayListItem = new JSONObject(ja.get(0).toString());			
	
		Assert.assertEquals("Afghanistan", firstArrayListItem.getString("name"), "name key value is matched");
		Assert.assertEquals("AFG", firstArrayListItem.getString("alpha3_code"), "alpha3_code key value is matched");
		Assert.assertEquals("AF", firstArrayListItem.getString("alpha2_code"), "alpha3_code key value is matched");
		
		logger.log(LogStatus.PASS, "Parse JSON array test passed");

	}

	
//
//	@Test
//	public void jsonPath() {
//		final String JSON = "{\n" + "\"lotto\":{\n" + " \"lottoId\":5,\n" + "}\n" + "}";
//		JsonPath jsonPath = new JsonPath(JSON).setRoot("lotto");
//
//		Assert.assertEquals(jsonPath.getInt("lottoId"), equals(5));
//	}

	
	private JSONObject getApiResponce(String restAPIUrl, String body) {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		// JSONObject jb = null;
		JSONObject jSONResponseBody = null;

		try {
			// Set API's Body
			builder.setBody(body);

			// Setting content type as application/json
			builder.setContentType("application/json; charset=UTF-8");
			RequestSpecification requestSpec = builder.build();
			Response response = RestAssured.given().authentication().preemptive().basic("", "").spec(requestSpec).when()
					.post(restAPIUrl);
			
			// RestAssured.get(restAPIUrl).then().assertThat().body(response.body(),
			// hasIt)
			// RestAssured.with().parameters(parametersMap)
			// RestAssured.given().cookie(cookie)
			// RestAssured.given().header(header)
			// RestAssured.)
			// RestAssured.given().contentType(contentType)
			
			
			jSONResponseBody = new JSONObject(response.body().asString());

			System.out.println("response = " + response);
			System.out.println("JSONResponseBody = " + jSONResponseBody);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jSONResponseBody;
	}
}
