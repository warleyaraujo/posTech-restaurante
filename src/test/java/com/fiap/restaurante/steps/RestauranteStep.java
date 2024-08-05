package com.fiap.restaurante.steps;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.utils.RestauranteHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RestauranteStep {

    private Response response;
    private Restaurante restauranteResponse;
    private String ENDPOINT_RESTAURANTES = "http://localhost:8080/restaurantes";


    @Quando("Submeter um novo restaurante")
    public Restaurante submeterNovoRestaurante() {
        var restauranteRequest = RestauranteHelper.gerarRegistro();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteRequest)
                .when().post(ENDPOINT_RESTAURANTES);
        return response.then().extract().as(Restaurante.class);
    }

    @Entao("O Restaurante é registrado com sucesso")
    public void restauranteRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestauranteResponseSchema.json"));
    }

    @Dado("Que um restaurante ja foi cadastrado")
    public void que_um_restaurante_ja_cadastrado() {
        restauranteResponse = submeterNovoRestaurante();
    }

    @Dado("Requisitar a exclusão do restaurante")
    public void requisitar_a_exclusao_do_restaurante() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/restaurantes/{id}", restauranteResponse.getId().toString());
    }

    @Entao("O restaurante é removido com sucesso")
    public void o_restaurante_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Quando("requisitar a alteracao do restaurante")
    public void requisitar_a_alteracao_do_restaurante() {
        restauranteResponse.setRazaoSocial("RESTAURANTE TESTE");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteResponse)
                .when()
                .put("/restaurantes/{id}", restauranteResponse.getId().toString());
    }
    @Entao("O restaurante é atualizado com sucesso")
    public void o_restaurante_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestauranteResponseSchema.json"));
    }

    @Quando("Requisitar a busca do restaurante")
    public void requisitar_a_busca_do_restaurante() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/restaurantes/{id}", restauranteResponse.getId().toString());
    }
    @Entao("O restaurante é exibido com sucesso")
    public void o_restaurante_e_exibido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestauranteResponseSchema.json"));
    }
}
