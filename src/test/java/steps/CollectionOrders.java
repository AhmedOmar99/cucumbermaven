package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static steps.KeycloakRestAssuredSSOLoginAutomation.performSSOLogin;

public class CollectionOrders {

    private static final String PORTAL_BASE_URL = "https://emoney-portal-fintech-dev.maxdev.org/emoney/api/v1";
    private static final String accessToken = performSSOLogin();
    int coID = 15559;

    @Given("User is authorized with credentials")
    public static String checkUserIsAuthorized() {
        System.out.println("Bearer " + accessToken);
        return accessToken;
    }

    @When("user sends the request to change the value of partial flag")
    public void changePartialFlagOfCO() {

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"newPartialFlag\": false,\n" +
                        "    \"newDate\": null,\n" +
                        "    \"collectionOrderIds\": [\n" +
                              coID +
                        "    ]\n" +
                        "}")
                .patch(PORTAL_BASE_URL + "/collection-orders/update-collection-orders")
                .then()
                .statusCode(200)
                .assertThat()
                .body("failedOrdersData", is(nullValue()))
                .log().all();
    }

    @Then("partial flag should be changed")
    public void checkTheValueOfPartialFlagChanged() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"collectionOrderIds\": [\n" +
                        coID+
                        "  ]\n" +
                        "}")
                .post(PORTAL_BASE_URL + "/collection-orders")
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].canCollectPartially", equalTo(false))
                .log().all();
    }
}
