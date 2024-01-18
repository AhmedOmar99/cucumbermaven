package steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class KeycloakRestAssuredSSOLoginAutomation {
    public static void main(String[] args) {
        // Set Keycloak login URL and client ID
        String keycloakBaseUrl = "https://sso.maxab.org/auth";
        String clientId = "e-money-portal";
        // Set Keycloak realm and token endpoint
        String realm = "maxab";
        String tokenEndpoint = "/realms/" + realm + "/protocol/openid-connect/token";

        // Set Keycloak credentials (client ID and secret)
        String clientSecret = "";

        // Set Keycloak user credentials
        String username = "ahmed.dahab@maxab.io";
        String password = "rxVpJx8dLZ38XNZ";

        // Perform SSO login using Rest Assured
        String accessToken = performSSOLogin();

        // Now, you can use the obtained access token for further actions in your application
        System.out.println("Access Token: " + accessToken);
    }

    public static String performSSOLogin() {
        return RestAssured.given()
                .baseUri("https://sso.maxab.org/auth")
                .basePath("/realms/maxab/protocol/openid-connect/token")
                .auth().preemptive().basic("e-money-portal", "")
                .formParam("grant_type", "password")
                .formParam("client_id", "e-money-portal")
                .formParam("username", "ahmed.dahab@maxab.io")
                .formParam("password", "rxVpJx8dLZ38XNZ")
                .contentType(ContentType.URLENC)
                .post()
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }
}
