#!/bin/bash
JAVAFX_SDK_PATH=./javafx-sdk 
OUT_DIR=out
MAIN_CLASS="com.Unipampa.ThePacoca" # Altere para sua classe principal

echo "Running JavaFX application..."
java --module-path $JAVAFX_SDK_PATH/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     -cp $OUT_DIR \
     $MAIN_CLASS