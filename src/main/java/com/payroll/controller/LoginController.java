package com.payroll.controller;

import com.payroll.App;
import com.payroll.common.Utils;
import com.payroll.persistence.UserEntity;
import com.payroll.service.CookieService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends BaseController
{
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private CheckBox rememberMe;

    @FXML
    private Button loginBtn;

    @FXML
    private Label errorMessage;

    private FadeTransition errorMessageFade;

    @Override
    public void initialize()
    {
        errorMessageFade = Utils.fadeOut(errorMessage, 2000);;

        //if (CookieService.getInstance().getStore().getBoolean("rememberMe", false)
        //&& CookieService.getInstance().getStore().get("username", "").equals(User.DefaultUsername)
        //&&


        super.initialize();
    }

    public void userLogin(ActionEvent event)
        throws IOException
    {
        try
        {
            loginBtn.setDisable(true);
            if (auth(username.getText(), password.getText()))
            {
                rememberLogin();
                App.loadScene("main");
            }
        }
        catch (IllegalArgumentException e)
        {
            errorMessage.setText(e.getMessage());
            errorMessageFade.playFromStart();
        }
        finally
        {
            loginBtn.setDisable(false);
        }
    }

    private boolean auth(String user, String pass)
    {
        if (username.getText().isEmpty() || password.getText().isEmpty())
            throw new IllegalArgumentException("Username or password is empty!");

        if (user.equals(UserEntity.DefaultUsername) && Utils.verifyHash(pass, UserEntity.DefaultPasswordHash))
            return true;

        throw new IllegalArgumentException("Username or password is wrong!");
    }

    private void rememberLogin()
    {
        if (rememberMe.isSelected())
        {
            String token = Utils.hashString(password.getText());

            CookieService.getInstance().getStore().put("username", username.getText());
            CookieService.getInstance().getStore().put("token", token);
            CookieService.getInstance().getStore().putBoolean("rememberMe", true);
        }
    }

    public static void forgetLogin()
    {
        CookieService.getInstance().getStore().remove("username");
        CookieService.getInstance().getStore().remove("token");
        CookieService.getInstance().getStore().remove("rememberMe");
    }
}
