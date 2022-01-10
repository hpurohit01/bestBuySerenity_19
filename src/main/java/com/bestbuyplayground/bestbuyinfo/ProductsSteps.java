package com.bestbuyplayground.bestbuyinfo;

import com.bestbuyplayground.constants.EndPoints;
import com.bestbuyplayground.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {

    @Step("Creating product with name : {0}, type: {1}, price: {2}, shipping: {3},upc: {4},description: {5},manufacturer: {6},model:{7},url: {8},image:{9}")
    public ValidatableResponse createProduct(String name, String type, double price,
                                             double shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting the product information with name: {0}")
    public HashMap<String, Object> getProductInfoByName(String name) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Updating  product  information with productID: {0}, name: {1},type: {2}, price: {3},shipping: {4},upc:{5},description: {6},manufacturer: {7},model: {8},url: {9},image:{10}")
    public ValidatableResponse updateProduct(int productId, String name, String type, double price, double shipping, String upc,
                                             String description, String manufacturer, String model,
                                             String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then();
    }


    @Step("Deleting product information with productID: {0}")
    public ValidatableResponse deleteProduct(int productID) {
        return SerenityRest
                .given()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then();

    }

    @Step("Getting product information with productId: {0}")
    public ValidatableResponse getProductById(int productId) {
        return SerenityRest
                .given()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

}
