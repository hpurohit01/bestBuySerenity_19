package com.bestbuyplayground.productinfo;

import com.bestbuyplayground.bestbuyinfo.ProductsSteps;
import com.bestbuyplayground.testbase.ProductTestBase;
import com.bestbuyplayground.utilis.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class ProductCRUDTestWithSteps extends ProductTestBase {

    static String name = "Sony AAA Batteries(4 PACK)" + TestUtils.getRandomValue();
    static String type ="HardGood" + TestUtils.getRandomValue();
    static double price = 7.99;
    static double shipping = 1.00;
    static String upc = "123488" +TestUtils.getRandomValue();// "upc": "12343342987413884"
    static String description ="Compatible  with select electronic devices" + TestUtils.getRandomValue();
    static String manufacturer ="Sonic" + TestUtils.getRandomValue();
    static String model = "SM2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String image = "\"http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productID;

    @Steps
    ProductsSteps productSteps;

    @Title("This will create a new product")
    @Test
    public void test001(){

        ValidatableResponse response = productSteps.createProduct(name,type,price,shipping,upc,description,manufacturer,model,url,image);
        response.log().all().statusCode(201);

    }

    @Title("Verify  the product was added to productlist")
    @Test
    public void test002(){
        HashMap<String, Object> value = productSteps.getProductInfoByName(name);
        System.out.println("name = " + name);
        System.out.println(value);
        Assert.assertThat(value, hasValue(name));
        productID = (int) value.get("id");
    }

    @Title("Update product information and verify the updated product information")
    @Test
    public void test003(){
        name = name + "_updated";
        productSteps.updateProduct(productID,name,type,price,shipping,upc,
                description,manufacturer, model,url,image);
        HashMap<String,Object> value = productSteps.getProductInfoByName(name);
        Assert.assertThat(value,hasValue(name));
    }


    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test004(){
        productSteps.deleteProduct(productID).statusCode(200);
        productSteps.getProductById(productID).statusCode(404);

    }

}
