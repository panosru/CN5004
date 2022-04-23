package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.Utils;
import com.cn5004ap.payroll.persistence.UserEntity;
import com.cn5004ap.payroll.service.CookieService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController
    extends BaseController
{
    @FXML
    private MFXTextField username;

    @FXML
    private MFXPasswordField password;

    @FXML
    private CheckBox rememberMe;

    @FXML
    private MFXButton loginBtn;

    @FXML
    private Label errorMessage;

    private FadeTransition errorMessageFade;

    private int attempts;

    public static void forgetLogin()
    {
        CookieService.getInstance().getStore().remove("username");
        CookieService.getInstance().getStore().remove("token");
        CookieService.getInstance().getStore().remove("rememberMe");
    }

    @Override
    public void initialize()
    {
        // Attach animation to error message label
        errorMessageFade = Utils.fadeOut(errorMessage, 1000);

        // Reset the label on animation complete
        errorMessageFade.setOnFinished(
            e ->
            {
               errorMessage.setText("");
               errorMessage.setOpacity(1);
               errorMessageFade.jumpTo(Duration.ZERO);
               errorMessageFade.stop();
               loginBtn.setDisable(false);
           });

        // Submit login form on Enter keyUp
        username.setOnKeyReleased(this::submitOnEnter);
        password.setOnKeyReleased(this::submitOnEnter);

        //if (CookieService.getInstance().getStore().getBoolean("rememberMe", false)
        //&& CookieService.getInstance().getStore().get("username", "").equals(User.DefaultUsername)
        //&&

        super.initialize();
    }

    private void submitOnEnter(KeyEvent event)
    {
        if (loginBtn.isDisabled())
            return;

        if (event.getCode().equals(KeyCode.ENTER))
            try
            {
                userLogin();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public void userLogin()
        throws IOException
    {
        loginBtn.textProperty().unbind();

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
        }
        finally
        {
            if (5 <= ++attempts)
            {
                username.setDisable(true);
                password.setDisable(true);
                rememberMe.setDisable(true);
                errorMessage.setText("Maximum login attempts made (5).");
            }
            else
            {
                Utils.runJobTimeoutAsync(() -> errorMessageFade.play(), 1000 * attempts);
            }
        }
    }

    private boolean auth(String user, String pass)
    {
        if (user.isEmpty() || pass.isEmpty())
            throw new IllegalArgumentException("Please fill both username and password.");

        if (!user.equals(UserEntity.DefaultUsername) && !Utils.verifyHash(pass, UserEntity.DefaultPasswordHash))
            throw new IllegalArgumentException("Invalid credentials.");

        return true;
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
}
