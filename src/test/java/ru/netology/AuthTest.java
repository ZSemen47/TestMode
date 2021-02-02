package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void openLink(){ open("http://localhost:9999/");}

    @Test
    void shouldLogin(){
        RegistrationInfo registrationInfo = DataGenerator.generateActiveUser();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    void notLoginCauseUserNotExist(){
        RegistrationInfo registrationInfo = DataGenerator.generateUserWhoNotExist();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь не зарегистрирован"));
    }

    @Test
    void notLoginBlockedUser(){
        RegistrationInfo registrationInfo = DataGenerator.generateBlockedUser();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void notEnterCauseInvalidLogin(){
        RegistrationInfo registrationInfo = DataGenerator.generateInvalidLogin();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void notEnterCauseInvalidPassword(){
        RegistrationInfo registrationInfo = DataGenerator.generateInvalidPassword();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void notEnterCauseNoLogin(){
        RegistrationInfo registrationInfo = DataGenerator.generateEmptyLogin();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $(".input_invalid[data-test-id='login'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void notEnterCauseNoPassword(){
        RegistrationInfo registrationInfo = DataGenerator.generateEmptyPassword();
        $("[data-test-id='login'] [name='login']").setValue(registrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(registrationInfo.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $(".input_invalid[data-test-id='password'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
