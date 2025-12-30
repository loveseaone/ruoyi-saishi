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
 * 活动报名Controller测试类
 * 测试ActivityRegistrationController中的所有接口
 */
@Slf4j
class ActivityRegistrationControllerTest extends BaseTest {

    @Test
    public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("participantName", "张三")
                .get("/activity/registration/list");
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
                .get("/activity/registration/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testAdd() {
        String body = "{\n" +
                "    \"activityId\": 1,\n" +
                "    \"participantName\": \"张三\",\n" +
                "    \"participantPhone\": \"13800138000\",\n" +
                "    \"participantEmail\": \"zhangsan@example.com\",\n" +
                "    \"participantOrg\": \"测试单位\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/registration");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"regId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"participantName\": \"李四\",\n" +
                "    \"participantPhone\": \"13800138001\",\n" +
                "    \"participantEmail\": \"lisi@example.com\",\n" +
                "    \"participantOrg\": \"测试单位\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/registration");
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
                .delete("/activity/registration/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}