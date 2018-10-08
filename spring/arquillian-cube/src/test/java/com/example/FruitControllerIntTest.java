package com.example;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;

import java.net.URL;

import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.arquillian.cube.openshift.impl.requirement.RequiresOpenshift;
import org.arquillian.cube.requirement.ArquillianConditionalRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;

@Category(RequiresOpenshift.class)
@RequiresOpenshift
@RunWith(ArquillianConditionalRunner.class)
public class FruitControllerIntTest {

	@AwaitRoute
	@RouteURL("${app.name}")
	private URL baseURL;

	@Before
	public void setup() throws Exception {
		RestAssured.baseURI = baseURL.toString();
	}

	@Test
	public void shouldGetAllFruits_Test() {
		when().get().then().statusCode(200).body("name", hasItems("Cherry", "Apple", "Banana"));
	}

}
