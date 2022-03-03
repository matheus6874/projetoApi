package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Restricoes {
    public Response consultaRestricaoCpf(String cpf){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/api/v1/restricoes/"+cpf);
        return response;
    }
}
