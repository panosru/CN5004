package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public abstract class BaseController
{
    @FXML
    protected BorderPane rootPane;

    @FXML
    protected AnchorPane moduleRoot;

    @FXML
    protected AnchorPane childRoot;

    public void initialize()
    {
        App.enableDrag(rootPane);
    }

    protected void loadModule(String fxml)
    {
        // Remove existing right content
        rootPane.getChildren().remove(rootPane.getRight());

        try
        {
            // Load pane
            Node node = App.loadNode(fxml);
            rootPane.setRight(node);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void loadModulePane(String fxml)
    {
        try
        {
            if (moduleRoot == null && childRoot != null)
                moduleRoot = (AnchorPane) childRoot.getParent().lookup("#moduleRoot");

            // Load pane
            Node node = App.loadNode(fxml);
            moduleRoot.getChildren().setAll(node);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
}
