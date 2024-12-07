package tests;

import models.UserInfoResponseRootModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LogSpec.*;

@Tag("REGRESS")
@DisplayName("Получение информации по пользователю")
public class GetUserInfoTests extends TestBase{

    @DisplayName("Успешное получение информации по пользователю")
    @Test
    void singleUserInfoTest(){

        UserInfoResponseRootModel response = step("Отправляем запрос на существующего пользователя", () ->
                given(RequestSpec)
                        .get("/users/2")

                        .then()
                        .spec(ResponseSpec200)
                        .extract().as(UserInfoResponseRootModel.class));

        step("Пользоваетль успешно создан, имя и должнотсь соответствует вводу", () -> {
            assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
            assertEquals(2, response.getData().getId());
            assertEquals("Weaver", response.getData().getLast_name());
        });
    }

    @DisplayName("Запрос на несуществующего пользователя")
    @Test
    void singleUserNotFoundTest(){

        step("Отправляем запрос на несуществующего пользователя", () ->
                given(RequestSpec)
                        .get("/users/22")

                        .then()
                        .spec(ResponseSpec404));
    }
}