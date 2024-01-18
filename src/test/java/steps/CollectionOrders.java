package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static steps.KeycloakRestAssuredSSOLoginAutomation.performSSOLogin;

public class CollectionOrders {
    static String accessToken = performSSOLogin();
    String portalBaseUrl = "https://emoney-portal-fintech-dev.maxdev.org/emoney/api/v1";

    @Given("User is authorized with credentials")
    public static String checkUserIsAuthorized() {
        System.out.println("Bearer "+accessToken);
        return accessToken;
    }

    @When("user send the request to change the value of partial flag")
    public void changePartialFlagOfCO() {
        String accessToken = performSSOLogin();
        System.out.println("Access token: " + accessToken);
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"newPartialFlag\": true,\n" +
                        "    \"newDate\": null,\n" +
                        "    \"collectionOrderIds\": [\n" +
                        "        15534\n" +
                        "    ]\n" +
                        "}")
                .patch(portalBaseUrl+"/collection-orders/update-collection-orders")
                .then()
                .statusCode(200)
                .assertThat()
                .body("failedOrdersData", is(nullValue()))
                .log().all();
    }

    @Then("partial flag should changed")
    public void checkTheValueOfPartialFlagChanged() {
        String accessToken = performSSOLogin();
        System.out.println("Access token: " + accessToken);
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"newPartialFlag\": true,\n" +
                        "    \"newDate\": null,\n" +
                        "    \"collectionOrderIds\": [\n" +
                        "        15534\n" +
                        "    ]\n" +
                        "}")
                .post(portalBaseUrl+"/collection-orders")
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].canCollectPartially", equalTo(true))
                .log().all();
    }
}
