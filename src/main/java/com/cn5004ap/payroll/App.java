package com.cn5004ap.payroll;

import com.cn5004ap.payroll.common.GlobalDTO;
import com.cn5004ap.payroll.common.Utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
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
            Font.loadFont(getResource("fonts/Arial.ttf").toExternalForm(), 12);
        }

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        loadScene("login");
        stage.show();
    }

    public static Node loadNode(String fxml)
        throws IOException
    {
        return FXMLLoader.load(getResource(String.format("view/%s.fxml", fxml)));
    }

    public static void loadScene(String fxml)
        throws IOException
    {
        Parent pane = (Parent) loadNode(fxml);
        getStage().setScene(new Scene(pane));
    }

    public static URL getResource(String path)
    {
        return Objects.requireNonNull(
            App.class.getResource(path));
    }

    public static GlobalDTO getUserData()
    {
        // Lazy loading
        if (null == stage.getUserData())
            stage.setUserData(new GlobalDTO());

        return (GlobalDTO) stage.getUserData();
    }

    public static Stage getStage()
    {
        return stage;
    }

    public static void enableDrag(Pane pane)
    {
        if (pane != null)
            pane.setOnMousePressed(pressEvent -> pane.setOnMouseDragged(dragEvent -> {
                getStage().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                getStage().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            }));
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
