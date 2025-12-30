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
 * 活动队伍Controller测试类
 * 测试ActivityTeamsController中的所有接口
 */
@Slf4j
class ActivityTeamsControllerTest extends BaseTest {

    @Test
    public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("teamName", "测试队")
                .get("/activity/teams/list");
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
                .get("/activity/teams/1");
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
                "    \"teamName\": \"测试队\",\n" +
                "    \"groupCode\": \"A\",\n" +
                "    \"displayOrder\": 1,\n" +
                "    \"leaderName\": \"张三\",\n" +
                "    \"leaderPhone\": \"13800138000\",\n" +
                "    \"coachName\": \"李四\",\n" +
                "    \"unionContact\": \"王五\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/teams");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"teamId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"teamName\": \"修改后的测试队\",\n" +
                "    \"groupCode\": \"B\",\n" +
                "    \"displayOrder\": 2,\n" +
                "    \"leaderName\": \"张三\",\n" +
                "    \"leaderPhone\": \"13800138000\",\n" +
                "    \"coachName\": \"李四\",\n" +
                "    \"unionContact\": \"王五\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/teams");
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
                .delete("/activity/teams/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}