package utility.invisible_noise_generator;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrimaryController implements Initializable {

	@FXML
	Label status;
	@FXML
	Button startStopBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		togglePlayState();
		SystemTray systemTray = SystemTray.getSystemTray();

		Image image = Toolkit.getDefaultToolkit()
				.getImage("src/main/resources/utility/invisible_noise_generator/icon.ico");
		PopupMenu trayPopupMenu = new PopupMenu();
		MenuItem action = new MenuItem("Open");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Platform.runLater(()->{App.show();});
			}
		});
		trayPopupMenu.add(action);
		MenuItem close = new MenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		trayPopupMenu.add(close);
		TrayIcon trayIcon = new TrayIcon(image, "Invisible Noise Generator", trayPopupMenu);
		trayIcon.setImageAutoSize(true);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}

	@FXML
	public void togglePlayState() {
		if (SoundUtils.isPlaying()) {
			SoundUtils.stop();
			status.setText("stopped");
			startStopBtn.setText("start");
		} else {
			SoundUtils.play();
			status.setText("running");
			startStopBtn.setText("stop");
		}
	}

}