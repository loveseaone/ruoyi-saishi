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
 * 活动公告Controller测试类
 * 测试ActivityNoticesController中的所有接口
 */
@Slf4j
class ActivityNoticesControllerTest extends BaseTest {

    @Test
    public void testList() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .param("title", "测试公告")
                .get("/activity/notices/list");
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
                .get("/activity/notices/1");
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
                "    \"title\": \"测试公告\",\n" +
                "    \"content\": \"这是测试公告的内容\",\n" +
                "    \"isPinned\": 0\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/activity/notices");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void testEdit() {
        String body = "{\n" +
                "    \"noticeId\": 1,\n" +
                "    \"activityId\": 1,\n" +
                "    \"title\": \"修改后的测试公告\",\n" +
                "    \"content\": \"这是修改后的测试公告的内容\",\n" +
                "    \"isPinned\": 1\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/activity/notices");
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
                .delete("/activity/notices/1");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}