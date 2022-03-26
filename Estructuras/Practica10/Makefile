
compile:
	javac --module-path $(PATH_TO_FX) --add-modules=javafx.controls,javafx.fxml -d ./build --source-path ./src src/ed/visualización/*.java
	javac --module-path $(PATH_TO_FX) --add-modules=javafx.controls,javafx.fxml -d ./build --source-path ./src src/ed/estructuras/**/*.java
	cp ./src/ed/visualización/arbolesscene.fxml ./build/ed/visualización/
	cp ./src/ed/visualización/styles.css ./build/ed/visualización/

run:
	java --module-path $(PATH_TO_FX) --add-modules=javafx.controls,javafx.fxml -classpath build ed.visualización.ApÁrboles

.PHONY: clean
clean:
	rm -rf build
