package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.ModalFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
        ModalFactory.setOwner(rootPane);
    }

    protected void loadComponent(String fxml)
    {
        // Remove existing right content
        rootPane.getChildren().remove(rootPane.getRight());

        // Load pane
        Node node = App.loadNode(fxml);
        rootPane.setRight(node);
    }

    protected void loadModule(String fxml)
    {
        // Load pane
        Node node = App.loadNode(fxml);
        if (null == moduleRoot)
            moduleRoot = (AnchorPane) childRoot.getParent();

        moduleRoot.getChildren().setAll(node);
    }
}
