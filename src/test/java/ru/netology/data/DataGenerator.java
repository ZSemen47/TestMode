package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void setUpAll(RegistrationInfo registrationInfo) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registrationInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static RegistrationInfo generateActiveUser(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "active"));
        return new RegistrationInfo(login, password, "active");
    }

    public static RegistrationInfo generateUserWhoNotExist(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        return new RegistrationInfo(login, password, "");
    }

    public static RegistrationInfo generateBlockedUser(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "blocked"));
        return new RegistrationInfo(login, password, "blocked");
    }

    public static RegistrationInfo generateEmptyLogin(){
        Faker faker = new Faker(new Locale("en"));
        String login = "";
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "active"));
        return new RegistrationInfo(login, password, "active");
    }

    public static RegistrationInfo generateEmptyPassword(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();;
        String password = "";
        setUpAll(new RegistrationInfo(login, password, "active"));
        return new RegistrationInfo(login, password, "active");
    }

    public static RegistrationInfo generateInvalidLogin(){
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo("Иииииигорь!", password, "active"));
        return new RegistrationInfo("login", password, "active");
        }

    public static RegistrationInfo generateInvalidPassword(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        setUpAll(new RegistrationInfo(login, "Ииииииигорь!", "active"));
        return new RegistrationInfo(login, "password", "active");
    }
    }