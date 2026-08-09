package api.tests.catfacts;

import api.BaseApiTest;
import api.enums.StatusCode;
import api.models.CatFactModel;
import api.models.CatFactsModel;
import api.services.CatFactService;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import utils.IConstants;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;

public class CatFactsTests extends BaseApiTest {

    private final CatFactService catFactService = new CatFactService();

    @Description("Check length of fact in response after get '/fact' request with max_length parameter")
    @Test
    public void isLengthInResponseEqualsOrLessThanLengthInQueryParameter(){
        CatFactModel model = catFactService.getFactWithLength(IConstants.maxLength)
                        .as(CatFactModel.class);
        assertThat(model.length(), Matchers.lessThanOrEqualTo(IConstants.maxLength));
    }

    @Description("Check length of fact in response after get '/fact' request with max_length parameter")
    @Test
    public void isFactLengthEqualsLengthInResponseBody(){
        CatFactModel model = catFactService.getFactWithLength(IConstants.maxLength)
                        .as(CatFactModel.class);
        assertThat(model.fact().length(), Matchers.lessThanOrEqualTo(model.length()));
    }

    @Description("Check response status code after get '/fact' request")
    @Test
    public void checkStatusCodeGetFact(){
        Response factResponse = catFactService.getFact();
        assertThat(factResponse.statusCode(), Matchers.equalTo(StatusCode.OK.getCode()));
    }

    @Description("Check response status code after get '/facts' request")
    @Test
    public void checkStatusCodeGetFacts(){
        Response factsResponse = catFactService.getFacts();
        assertThat(factsResponse.statusCode(), Matchers.equalTo(StatusCode.OK.getCode()));
    }

    @Description("Check lengths of facts in response after get '/facts' request with max_length parameter")
    @Test
    public void areLengthsInResponseEqualsOrLessThanLengthInQueryParameter(){
        CatFactsModel responseFacts = catFactService.getFactsWithLength(IConstants.maxLength)
                        .as(CatFactsModel.class);
        Arrays.stream(responseFacts.data())
                .forEach(fact -> assertThat(fact.length(), Matchers.lessThanOrEqualTo(IConstants.maxLength)));
    }

    @Description("Check amount of facts after get '/facts' request with limit parameter")
    @Test
    public void isSizeOfFactsInResponseEqualsLimitInQueryParameter(){
        CatFactsModel responseFacts = catFactService.getFactsWithLimit(IConstants.limit)
                        .as(CatFactsModel.class);
        assertThat(responseFacts.data().length, Matchers.equalTo(IConstants.limit));
    }
}