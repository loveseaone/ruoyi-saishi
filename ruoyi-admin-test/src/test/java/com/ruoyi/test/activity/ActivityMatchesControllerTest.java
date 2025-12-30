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
 * 活动比赛场次Controller测试类
 * 测试ActivityMatchesController中的所有接口
 */
@Slf4j
class ActivityMatchesControllerTest extends BaseTest {

    @Test
public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("matchCode", "1")
                .get("/activity/matches/list");
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
                .get("/activity/matches/1");
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
                "    \"matchCode\": \"001\",\n" +
                "    \"stage\": 1,\n" +
                "    \"format\": 3,\n" +
                "    \"roundDesc\": \"小组赛\",\n" +
                "    \"groupA\": \"A\",\n" +
                "    \"teamAId\": 1,\n" +
                "    \"teamBId\": 2,\n" +
                "    \"court\": 1,\n" +
                "    \"matchDate\": \"2025-12-01\",\n" +
                "    \"startTime\": \"09:00:00\",\n" +
                "    \"status\": 0\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/matches");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"matchId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"matchCode\": \"001\",\n" +
                "    \"stage\": 1,\n" +
                "    \"format\": 3,\n" +
                "    \"roundDesc\": \"小组赛\",\n" +
                "    \"groupA\": \"A\",\n" +
                "    \"teamAId\": 1,\n" +
                "    \"teamBId\": 2,\n" +
                "    \"court\": 1,\n" +
                "    \"matchDate\": \"2025-12-01\",\n" +
                "    \"startTime\": \"09:00:00\",\n" +
                "    \"status\": 1\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/matches");
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
                .delete("/activity/matches/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}