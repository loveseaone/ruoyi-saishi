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
 * 活动信息Controller测试类
 * 测试ActivityInfoController中的所有接口
 */
@Slf4j
class ActivityInfoControllerTest extends BaseTest {

    @Test
    public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("activityName", "羽毛球")
                .get("/activity/info/list");
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
                .get("/activity/info/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testAdd() {
        String body = "{\n" +
                "    \"activityName\": \"测试活动\",\n" +
                "    \"activityDesc\": \"这是一个测试活动\",\n" +
                "    \"activityType\": \"测试\",\n" +
                "    \"startTime\": \"2025-12-01 09:00:00\",\n" +
                "    \"endTime\": \"2025-12-02 18:00:00\",\n" +
                "    \"organizer\": \"测试主办方\",\n" +
                "    \"contactInfo\": \"0731-88888888\",\n" +
                "    \"location\": \"测试地点\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/info");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"activityId\": 1,\n" +
                "    \"activityName\": \"修改后的测试活动\",\n" +
                "    \"activityDesc\": \"这是修改后的测试活动\",\n" +
                "    \"activityType\": \"测试\",\n" +
                "    \"startTime\": \"2025-12-01 09:00:00\",\n" +
                "    \"endTime\": \"2025-12-02 18:00:00\",\n" +
                "    \"organizer\": \"测试主办方\",\n" +
                "    \"contactInfo\": \"0731-88888888\",\n" +
                "    \"location\": \"测试地点\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/info");
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
                .delete("/activity/info/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}