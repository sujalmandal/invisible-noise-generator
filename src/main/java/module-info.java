module utility.invisible_noise_generator {
    requires javafx.controls;
    requires transitive javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;

    opens utility.invisible_noise_generator to javafx.fxml;
    exports utility.invisible_noise_generator;
}