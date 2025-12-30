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
 * 活动积分排名Controller测试类
 * 测试ActivityStandingsController中的所有接口
 */
@Slf4j
class ActivityStandingsControllerTest extends BaseTest {

    @Test
    public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("groupCode", "A")
                .get("/activity/standings/list");
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
                .get("/activity/standings/1");
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
                "    \"teamId\": 1,\n" +
                "    \"groupCode\": \"A\",\n" +
                "    \"matchesPlayed\": 0,\n" +
                "    \"wins\": 0,\n" +
                "    \"losses\": 0,\n" +
                "    \"points\": 0,\n" +
                "    \"setsWon\": 0,\n" +
                "    \"setsLost\": 0,\n" +
                "    \"pointsFor\": 0,\n" +
                "    \"pointsAgainst\": 0\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/standings");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"standingId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"teamId\": 1,\n" +
                "    \"groupCode\": \"A\",\n" +
                "    \"matchesPlayed\": 1,\n" +
                "    \"wins\": 1,\n" +
                "    \"losses\": 0,\n" +
                "    \"points\": 3,\n" +
                "    \"setsWon\": 2,\n" +
                "    \"setsLost\": 0,\n" +
                "    \"pointsFor\": 21,\n" +
                "    \"pointsAgainst\": 10\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/standings");
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
                .delete("/activity/standings/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}