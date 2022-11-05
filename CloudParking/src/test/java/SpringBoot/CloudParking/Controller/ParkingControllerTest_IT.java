package SpringBoot.CloudParking.Controller;

import SpringBoot.CloudParking.Controller.DTO.ParkingCreateDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Matches;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest_IT extends AbstractContainerBase{

    //Para que serve o randomPort?
    //Serve carregar portas aleatórias para os testes, para não dar conflito com a porta principal.
    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }


    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .when()
                .auth()
                .basic("user","dio")
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("license[1]", Matchers.equalTo("DMS-1111"));
//                ==>Essa parte recupera o corpo do que estou buscando.
//                .extract().response().body().prettyPrint();

    }

    @Test
    void whencreateThenCheckIsCreated() {
        var createDTO= new ParkingCreateDTO();
        createDTO.setColor("Azul");
        createDTO.setLicense("ORG-6N52");
        createDTO.setModel("FUSCA");
        createDTO.setState("RS");

        RestAssured.given()
                .when()
                .auth()
                .basic("user","dio")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("ORG-6N52"))
                .body("color", Matchers.equalTo("Vermelho"));

    }
}