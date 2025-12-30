package com.ruoyi.test.activity;

import com.ruoyi.test.config.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * 活动运动员及工作人员Controller测试类
 * 测试ActivityAthletesController中的所有接口
 */
@Slf4j
class ActivityAthletesControllerTest extends BaseTest {

    @Test
    public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("name", "张三")
                .get("/activity/athletes/list");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testGetInfo() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .get("/activity/athletes/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testAdd() {
        String body = "{\n" +
                "    \"teamId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"name\": \"张三\",\n" +
                "    \"gender\": 0,\n" +
                "    \"phone\": \"13800138000\",\n" +
                "    \"idCard\": \"110101199003076577\",\n" +
                "    \"role\": \"男队员\",\n" +
                "    \"isOnField\": 1\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/athletes");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"athleteId\": 1,\n" +
                "    \"teamId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"name\": \"李四\",\n" +
                "    \"gender\": 1,\n" +
                "    \"phone\": \"13800138001\",\n" +
                "    \"idCard\": \"110101199003076578\",\n" +
                "    \"role\": \"女队员\",\n" +
                "    \"isOnField\": 1\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/athletes");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testRemove() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .delete("/activity/athletes/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}