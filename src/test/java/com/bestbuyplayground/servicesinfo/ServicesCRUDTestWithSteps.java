package com.bestbuyplayground.servicesinfo;

import com.bestbuyplayground.bestbuyinfo.ServicesSteps;
import com.bestbuyplayground.constants.EndPoints;
import com.bestbuyplayground.model.ServicesPojo;
import com.bestbuyplayground.testbase.ServicesTestBase;
import com.bestbuyplayground.utilis.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasValue;


    @RunWith(SerenityRunner.class)
    public class ServicesCRUDTestWithSteps extends ServicesTestBase {

        static String name = "Magnolia" + TestUtils.getRandomValue();
        static String servicesId;

        @Steps
        ServicesSteps servicesSteps;

        @Title("creating and verify the category data to an application")
        @Test
        public void test001(){

            HashMap<String,Object> pvalue = servicesSteps.createService(name)
                    .log().all().statusCode(201).extract().response().body().jsonPath().get();

            System.out.println(pvalue);
            assertThat(pvalue,hasValue(name));
            servicesId = (String) pvalue.get("id");
            System.out.println(servicesId);
        }

        @Title("Update and verify the data in category application")
        @Test
        public void test002(){

            name = name + "_Updated";

            ServicesPojo servicesPojo = new ServicesPojo();
            servicesPojo.setName(name);

            SerenityRest.rest().given().log().all()
                    .header("Content-Type", "application/json")
                    .pathParam("servicesId",servicesId)
                    .body(servicesPojo)
                    .when()
                    .put(EndPoints.UPDATE_SINGLE_SERVICE_BY_ID)
                    .then()
                    .log().all().statusCode(200);

            HashMap<String,Object> pvalue =
                    SerenityRest.rest().given().log().all()
                            .when()
                            .get(EndPoints.GET_ALL_SERVICES)
                            .then()
                            .log().all().statusCode(200)
                            .extract().response().body().jsonPath().get();
            assertThat(pvalue,hasValue(name));
        }

        @Title("Deleting and verify the product is deleted")
        @Test
        public void test003(){
            SerenityRest.rest().given().log().all()
                    .pathParam("servicesId",servicesId)
                    .when()
                    .delete(EndPoints.DELETE_SINGLE_SERVICE_BY_ID)
                    .then()
                    .statusCode(200);

            SerenityRest.rest().given().log().all()
                    .pathParam("servicesId",servicesId)
                    .when()
                    .get(EndPoints.GET_SINGLE_SERVICE_BY_ID)
                    .then()
                    .statusCode(404);
        }
}
