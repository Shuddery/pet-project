package utils;

public interface IConstants {

    // valid usernames + password
    String standardUsername = "standard_user";
    String lockedUsername = "locked_out_user";
    String password = "secret_sauce";

    // error messages + invalid credentials
    String lockedUserErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
    String wrongCredentialsMessage = "Epic sadface: Username and password do not match any user in this service";
    String wrongUsername = "wrong_username";
    String wrongPassword = "wrong_password";
    String emptyPasswordMessage = "Epic sadface: Password is required";
    String emptyUsernameMessage = "Epic sadface: Username is required";

    String titleOfLoginPage = "Swag Labs";
    String productsPagePath = "/inventory.html";
    String pathToResources = "src/test/resources/screenshots/";

    // query parameters
    int maxLength = 50;
    int limit = 3;
}