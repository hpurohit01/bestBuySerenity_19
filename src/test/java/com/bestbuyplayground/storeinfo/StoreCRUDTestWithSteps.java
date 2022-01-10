package com.bestbuyplayground.storeinfo;

import com.bestbuyplayground.bestbuyinfo.StoresSteps;
import com.bestbuyplayground.testbase.StoreTestBase;
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

    public class StoreCRUDTestWithSteps extends StoreTestBase {

    static String name = "Kingston";
    static String type = "BigBox";
    static String address = "121 High Road";
    static String address2 = "Kingston Road";
    static String city = "Kingston";
    static String state = "London";
    static String zip = "66905";
    static double lat = 44.969656;
    static double lng = -93.445579;
    static String hours= "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeID;

    @Steps
    StoresSteps storesSteps;
    @Title("This will create new store")
    @Test
    public void test001(){
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng,hours);
        response.log().all().statusCode(201);
    }


    @Title("Verify if the strore was created in stores list")
    @Test
    public void test002(){

        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        System.out.println("name = " + name);
        System.out.println(value);
        Assert.assertThat(value, hasValue(name));
        storeID = (int) value.get("id");


    }

    @Title("Update store information and verify the updated information")
    @Test
    public void test003(){
        name = name + "_updated";
        storesSteps.updateStore(storeID, name, type, address, address2, city, state, zip, lat, lng,hours);
        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(value, hasValue(name));

    }
    @Title("Delete store and verify if the product is deleted")
    @Test
    public void test004(){
        storesSteps.deleteStore(storeID).statusCode(200);
        storesSteps.getStoreById(storeID).statusCode(404);
    }


}
