package com.fiap.restaurante.steps;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.utils.ClienteHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ClienteStep {
    private Response response;
    private Cliente clienteResponse;
    private String ENDPOINT_CLIENTES = "http://localhost:8080/clientes";



    @Quando("submeter um novo cliente")
    public Cliente submeterNovoCliente() {
        var clienteRequest = ClienteHelper.gerarRegistro();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when().post(ENDPOINT_CLIENTES);
        return response.then().extract().as(Cliente.class);
    }

    @Entao("o cliente e registrado com sucesso")
    public void clienteRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Dado("que um cliente ja foi cadastrado")
    public void que_um_cliente_ja_foi_cadastrado() {
        clienteResponse = submeterNovoCliente();
    }
    @Quando("requisitar a exclusao do cliente")
    public void requisitar_a_exclusao_do_cliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/clientes/{id}", clienteResponse.getId().toString());
    }
    @Entao("o clente e removido com sucesso")
    public void o_clente_e_removido_com_sucesso() {
        response.then()
                .statusCode((HttpStatus.OK.value()));
    }

    @Quando("requisitar a alteracao do cliente")
    public void requisitar_a_alteracao_do_cliente() {
        clienteResponse.setEmail("testeCucumber@gmail.com");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteResponse)
                .when()
                .put("/clientes/{id}", clienteResponse.getId().toString());
    }
    @Entao("o cliente e atualizado com sucesso")
    public void o_cliente_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Quando("requisitar a busca do cliente")
    public void requisitar_a_busca_do_cliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/clientes/{id}", clienteResponse.getId().toString());
    }
    @Entao("o cliente e exibido com sucesso")
    public void o_cliente_e_exibido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }


}
