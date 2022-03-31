package com.payroll;

import com.payroll.common.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App
    extends Application
{
    /**
     * Scene placeholder
     */
    private static Stage stage;

    @Override
    public void start(Stage primaryStage)
        throws Exception
    {
        stage = primaryStage;

        if (!(Utils.isWindows() || Utils.isMac()))
        {
            //Load Arial font for all *nix platforms
            Font.loadFont(
                Objects.requireNonNull(
                    getClass().getClassLoader().getResource("fonts/Arial.ttf")).toExternalForm(), 12);
        }

        stage.initStyle(StageStyle.UNDECORATED);
        //stage.setTitle(Utils.TITLE);
        stage.setResizable(false);
        loadScene("view/login.fxml");
        stage.show();
    }

    public static void loadScene(String fxml)
        throws IOException
    {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml)));
        getStage().setScene(new Scene(pane, Utils.APP_WIDTH, Utils.APP_HEIGHT));
    }

    public static void closeWindow()
    {
        getStage().close();
    }

    public static void exit()
    {
        Platform.exit();
    }

    public static Stage getStage()
    {
        return stage;
    }

    public static void enableDrag(BorderPane pane)
    {
        pane.setOnMousePressed(pressEvent -> {
            pane.setOnMouseDragged(dragEvent -> {
                getStage().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                getStage().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
