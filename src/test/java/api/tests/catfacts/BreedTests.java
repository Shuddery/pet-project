package api.tests.catfacts;

import api.BaseApiTest;
import api.enums.StatusCode;
import api.models.BreedsModel;
import api.services.BreedService;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import utils.IConstants;

import static org.hamcrest.MatcherAssert.assertThat;

public class BreedTests extends BaseApiTest {

    BreedService breedService = new BreedService();

    @Description("Check response status code after get '/breeds' request")
    @Test
    public void checkStatusCodeGetBreeds(){
        Response breedsResponse = breedService.getBreeds();
        assertThat(breedsResponse.statusCode(), Matchers.equalTo(StatusCode.OK.getCode()));
    }

    @Description("Check amount of breeds after get '/breeds' request with limit parameter")
    @Test
    public void isSizeOfBreedsInResponseEqualsLimitInQueryParameter(){
        BreedsModel responseBreeds = breedService.getBreedsWithQueryLimitParameter(IConstants.limit)
                        .as(BreedsModel.class);
        assertThat(responseBreeds.data().length, Matchers.equalTo(IConstants.limit));
    }
}