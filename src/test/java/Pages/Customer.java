package Pages;

import Utils.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;

public class Customer {
    Properties props = new Properties();
    FileInputStream file = new FileInputStream("./src/test/resources/config.properties");

    public Customer() throws FileNotFoundException {
    }

    public void customerLoginApi() throws ConfigurationException, IOException {
        props = new Properties();
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .body("{ \n" +
                                " \"username\":\"salman\", \n" +
                                " \"password\":\"salman1234\" \n" +
                                "} ")
                        .when()
                        .post("/customer/api/v1/login")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        String token = resObj.get("token");
        Utils.setEnvValue("token", token);
        System.out.println(token);

    }

    public void customerListApi() throws IOException, ConfigurationException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .headers("Authorization", props.getProperty("token"))
                        .when()
                        .get("/customer/api/v1/list")
                        .then()
                        .assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());
        JsonPath resObj = response.jsonPath();
        int id = resObj.get("Customers[0].id");
        Assert.assertEquals(101, id);


//        JsonPath resObj = response.jsonPath();
//        String token = resObj.get("token");
//        Utils.Utils.setEnvValue("token",token );
//        System.out.println(token);


    }

    public void searchCustomer() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response = given()
                .contentType("application/json")
                .headers("Authorization", props.getProperty("token"))
                .when()
                .get("/customer/api/v1/get/102")
                .then()
                .assertThat().statusCode(200).extract().response();

//        JsonPath jsonObj = response.jsonPath();
//        String name = jsonObj.get("name");
//        Assert.assertEquals("Mr. Kamal", name);
        System.out.println(response.asString());
    }

    public Integer ID;
    public String name;
    public String email;
    public String address;
    public String phone_number;

    public void GenerateCustomer() throws ConfigurationException, IOException {

        props.load(file);
        RestAssured.baseURI = "https://randomuser.me";
        Response res =

                given()
                        .contentType("application/json")
                        .when()
                        .get("/api")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = res.jsonPath();
        ID = (int) Math.floor(Math.random() * (999999 - 100000) + 1);
        name = "Salman " + resObj.get("results[0].name.first");
        email = resObj.get("results[0].email");
        address = resObj.get("results[0].location.state");
        phone_number = resObj.get("results[0].cell");
        Utils.setEnvValue("id", ID.toString());
        Utils.setEnvValue("name", name);
        Utils.setEnvValue("email", email);
        Utils.setEnvValue("address", address);
        Utils.setEnvValue("phone_number", phone_number);
        System.out.println(res.asString());
    }

    public void createCustomer() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .headers("Authorization", props.getProperty("token"))
                        .body("{\n" +
                                "    \" id\":" + ID + ",\n" +
                                "    \"name\": \"" + props.getProperty("name") + "\",\n" +
                                "    \"email\": \"" + props.getProperty("email") + "\",\n" +
                                "    \"address\": \"" + props.getProperty("address") + "\",\n" +
                                "    \"phone_number\": \"" + props.getProperty("phone_number") + "\"\n" +
                                "}")
                        .when()
                        .post("/customer/api/v1/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();
        System.out.println(response.asString());

    }

    public void customerUpdate() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .headers("Authorization", props.getProperty("token"))
                        .body("{\n" +
                                "            \"id\": 101,\n" +
                                "            \"name\": \"Mr. Uzzal Mondol\",\n" +
                                "            \"email\": \"mrkamal@test.com\",\n" +
                                "            \"address\": \"Bahrain,Da\",\n" +
                                "            \"phone_number\": \"01502020110\"\n" +
                                "        }")
                        .when().
                        put("/customer/api/v1/update/101")
                        .then()
                        .assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());


    }

    public void customerDelete() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .headers("Authorization", props.getProperty("token"))
                        .body("")
                        .when()
                        .delete("/customer/api/v1/delete/205172")
                        .then().assertThat().statusCode(404).extract().response();
        System.out.println(response.asString());
//        JsonPath jsonObj = response.jsonPath();
//        jsonObj.get();
    }

}

