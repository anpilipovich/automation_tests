import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTests {

    private static String id;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://anastasiya.free.beeceptor.com";
    }

    @Test
    void postTest() {
        String requestBody = """
                {
                  "email": "eve.holt@reqres.in",
                  "password": "cityslicka"
                }
                """;

        id = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("id");
    }

    @Test
    void createUserTest() {
        String requestBody = """
                {
                  "name": "morpheus",
                  "job": "leader"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    void getUserTest() {
        given()
                .when()
                .get("/api/users/1")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    void deleteTest() {
        given()
                .when()
                .delete("/api/users/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void beeceptorTest() {
        String body = """
                {
                  "name": "Anastasiya",
                  "id": "1"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }
}

