package com.ruoyi.test.wx;

import com.ruoyi.test.config.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * 会员信息Controller测试类
 * 测试MemberController中的所有接口
 */
@Slf4j
class MemberControllerTest extends BaseTest {

    @Test
    public void loginWithPassword() {
        String body = "{\n" +
                "    \"loginType\": \"password\",\n" +
                "    \"mobile\": \"13800138000\",\n" +
                "    \"password\": \"123456\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/wx/member/login");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void loginWithCaptcha() {
        String body = "{\n" +
                "    \"loginType\": \"captcha\",\n" +
                "    \"mobile\": \"13800138000\",\n" +
                "    \"captcha\": \"123456\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/wx/member/login");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void updatePassword() {
        String body = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"password\": \"newPassword123\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/wx/member/password");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void updateInfo() {
        String body = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"avatar\": \"http://example.com/avatar.jpg\",\n" +
                "    \"classes\": \"3班\",\n" +
                "    \"grade\": \"3\",\n" +
                "    \"studentNo\": \"S2025001\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/wx/member/info");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }

    @Test
    public void getInfo() {
        Response response = RestAssured.given()
                .header("Authorization", X_ACCESS_TOKEN)
                .get("/wx/member/info");
        log.info("response:{}", response.asString());
        response.then()
                .statusCode(200)
                .body("msg", notNullValue())
                .body("code", equalTo(200));
    }
}