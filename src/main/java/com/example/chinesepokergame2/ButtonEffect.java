package com.example.chinesepokergame2;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class ButtonEffect extends ButtonSkin {
    private boolean animationPlaying = false;

    public ButtonEffect(Button control) {
        super(control);

        control.setEffect(new ColorAdjust(0, 0, 0, 0));

        final ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100));
        scaleUp.setNode(control);
        scaleUp.setToX(1.04);
        scaleUp.setToY(1.04);

        final ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100));
        scaleDown.setNode(control);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        control.setOnMouseEntered(e -> scaleUp.playFromStart());
        control.setOnMouseExited(e -> scaleDown.playFromStart());

        control.setOnMousePressed(e -> {
            EventHandler<ActionEvent> originalAction = control.getOnAction();
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.20);
            control.setEffect(colorAdjust);

            control.setOnMouseReleased(event -> {
                control.setEffect(null);
                control.setOnAction(originalAction);
            });
            control.setOnMouseExited(event -> {
                scaleDown.playFromStart();
                control.setEffect(null);
                control.setOnAction(originalAction);
            });
        });
    }
}
