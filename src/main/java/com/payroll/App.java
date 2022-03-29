package com.payroll;

import com.payroll.common.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class App
    extends Application
{
    @Override
    public void start(Stage stage)
        throws Exception
    {
        if (!(Utils.isWindows() || Utils.isMac()))
        {
            //Load Arial font for all *nix platforms
            Font.loadFont(
                Objects.requireNonNull(
                    this.getClass().getClassLoader().getResource("fonts/Arial.ttf")).toExternalForm(), 12);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(
            this.getClass().getResource("view/login.fxml"));

        stage.setTitle(Utils.TITLE);
        stage.setScene(new Scene(fxmlLoader.load(), Utils.APP_WIDTH, Utils.APP_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
