package com.cn5004ap.payroll.common;

import com.cn5004ap.payroll.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.FontResources;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class ModalFactory
{
    private static Pane owner;

    private ModalFactory()
    { }

    public static @NotNull Modal create(
        @NotNull Type type,
        String title,
        String content,
        Level level,
        Map.Entry<Node, EventHandler<MouseEvent>>... actions)
    {
        return switch (type)
        {
            case SIMPLE -> new SimpleModal(title, content, level, owner, actions);
            case FXML -> new FXMLModal(title, content, level, owner, actions);
        };
    }

    public static void setOwner(Pane owner)
    {
        ModalFactory.owner = owner;
    }

    private static class SimpleModal
        extends Modal
    {
        public SimpleModal(
            String title,
            String content,
            Level level,
            Pane owner,
            Map.Entry<Node, EventHandler<MouseEvent>>... actions)
        {
            super(
                MFXGenericDialogBuilder
                    .build()
                    .setShowAlwaysOnTop(false)
                    .setShowMinimize(false)
                    .setShowClose(false)
                    .makeScrollable(false)
                    .get()
            );

            buildDialog(owner);
            setTitle(title);
            setContent(content);
            setLevel(level);
            setActions(actions);
        }
    }

    private static class FXMLModal
        extends Modal
    {
        public FXMLModal(
            String title,
            String fxml,
            Level level,
            Pane owner,
            Map.Entry<Node, EventHandler<MouseEvent>>... actions)
        {
            super((MFXGenericDialog) App.loadNode(fxml));

            dialogContent.setShowAlwaysOnTop(false);
            dialogContent.setShowMinimize(false);
            dialogContent.setShowClose(false);

            buildDialog(owner);
            setTitle(title);
            setLevel(level);
            setActions(actions);
        }
    }

    public static class Modal
    {
        protected MFXGenericDialog dialogContent;

        protected MFXStageDialog dialog;

        private Modal()
        { }

        protected Modal(MFXGenericDialog dialogContent)
        {
            this.dialogContent = dialogContent;
        }

        protected void buildDialog(Pane owner)
        {
            dialog = MFXGenericDialogBuilder
                .build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(App.getStage())
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setCenterInOwnerNode(true)
                .setOwnerNode(owner)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimStrength(0.75)
                .setScrimOwner(true)
                .get();
        }

        public MFXGenericDialog getDialogContent()
        {
            return dialogContent;
        }

        public MFXStageDialog getDialog()
        {
            return dialog;
        }

        public void setContent(String content)
        {
            dialogContent.setContentText(content);
        }

        public void setActions(Map.Entry<Node, EventHandler<MouseEvent>>... actions)
        {
            dialogContent.clearActions();
            dialogContent.addActions(actions);
        }

        public void setContent(Node content)
        {
            dialogContent.setContent(content);
        }

        public void setTitle(String title)
        {
            dialogContent.setHeaderText(title);
        }

        public void setLevel(Level level)
        {
            Pair<String, MFXFontIcon> pair = switch (level)
            {
                case GENERIC -> new Pair<>(null, null);
                case INFO -> new Pair<>(
                    "mfx-info-dialog", new MFXFontIcon(FontResources.INFO_CIRCLE_FILLED.getDescription(), 18));
                case WARNING -> new Pair<>(
                    "mfx-warn-dialog", new MFXFontIcon(FontResources.DO_NOT_ENTER_CIRCLE.getDescription(), 18));
                case ERROR -> new Pair<>(
                    "mfx-error-dialog", new MFXFontIcon(FontResources.EXCLAMATION_CIRCLE_FILLED.getDescription(), 18));
            };

            convertDialogTo(pair.getKey());
            dialogContent.setHeaderIcon(pair.getValue());
        }

        public void close()
        {
            dialog.close();
        }

        public void show()
        {
            dialog.showDialog();
        }

        public void convertDialogTo(String styleClass) {
            dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
            );

            if (styleClass != null)
                dialogContent.getStyleClass().add(styleClass);
        }

        public static class ActionButtons
        {
            public static MFXButton CONFIRM;
            public static MFXButton CANCEL;
            public static MFXButton OK;

            static
            {
                CONFIRM = new MFXButton("Confirm");
                CONFIRM.getStyleClass().add("modal-confirm-button");

                CANCEL = new MFXButton("Cancel");
                CANCEL.getStyleClass().add("modal-cancel-button");

                OK = new MFXButton("Ok");
                OK.getStyleClass().add("modal-ok-button");
            }
        }
    }

    public enum Type
    {
        SIMPLE,
        FXML
    }

    public enum Level
    {
        GENERIC,
        INFO,
        WARNING,
        ERROR
    }
}
