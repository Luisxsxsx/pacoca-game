package com.Unipampa;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TabuleiroView extends StackPane {

    public GridPane grid;

    public TabuleiroView() {
        this.grid = new GridPane(0, 0);

        // grid.setGridLinesVisible(true);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i + j) % 2 == 0) {
                    grid.add(new Rectangle(100, 100, Color.WHITE), i, j);
                } else {
                    grid.add(new Rectangle(100, 100, Color.BLACK), i, j);
                }
            }
        }
    }

}
