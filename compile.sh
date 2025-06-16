#!/bin/bash
JAVAFX_SDK_PATH=./javafx-sdk
OUT_DIR=out
SRC_DIR=src

mkdir -p $OUT_DIR

echo "Compiling JavaFX application..."
javac --module-path $JAVAFX_SDK_PATH/lib \
      --add-modules javafx.controls,javafx.fxml,javafx.graphics \
      -d $OUT_DIR \
      $(find $SRC_DIR -name "*.java")

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
else
    echo "Compilation failed."
fi