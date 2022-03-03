package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Simulacao {
    public Response insereSimulacao(String nome, String cpf,String email,Integer valor,Integer parcelas){
        RestAssured.baseURI = "http://localhost:8080";
        String payload = "{\n" +
                "  \"nome\": \""+nome+"\",\n" +
                "  \"cpf\": \""+cpf+"\",\n" +
                "  \"email\": \""+email+"\",\n" +
                "  \"valor\": "+valor+",\n" +
                "  \"parcelas\": "+parcelas+",\n" +
                "  \"seguro\": true\n" +
                "}";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type","application/json");
        Response response = httpRequest.body(payload).post("/api/v1/simulacoes");
        return response;
    }

    public Response alterarSimulacao(String nome, String cpf,String email,Integer valor,Integer parcelas){
        RestAssured.baseURI = "http://localhost:8080";
        String payload = "{\n" +
                "  \"nome\": \""+nome+"\",\n" +
                "  \"email\": \""+email+"\",\n" +
                "  \"valor\": "+valor+",\n" +
                "  \"parcelas\": "+parcelas+",\n" +
                "  \"seguro\": true\n" +
                "}";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type","application/json");
        Response response = httpRequest.body(payload).put("/api/v1/simulacoes/"+cpf+"");
        return response;
    }

    public Response consultarSimulacoesCadastradas(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/api/v1/simulacoes");
        return response;
    }

    public Response consultarSimulacaoPorCpf(String cpf){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/api/v1/simulacoes/"+cpf+"");
        return response;
    }

    public Response deletarSimulacao(String id){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/api/v1/simulacoes/"+id+"");
        return response;
    }
}
