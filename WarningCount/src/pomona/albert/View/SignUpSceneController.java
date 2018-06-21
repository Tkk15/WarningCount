package pomona.albert.View;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import pomona.albert.Controller.Controller;

public class SignUpSceneController {
	Controller controller = Controller.getInstance();
	@FXML
	private TextField nameTF;
	@FXML
	private Label nameErrorLabel;
	@FXML
	private TextField emailAddressTF;
	@FXML
	private Label emailErrorLabel;
	@FXML
	private TextField passwordTF;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Label signUpErrorLabel;
	@FXML
	public boolean signUp() {
		String name = nameTF.getText();
		String email = emailAddressTF.getText();
		String password = passwordTF.getText();

		nameErrorLabel.setVisible(name.isEmpty());
		emailErrorLabel.setVisible(email.isEmpty());
		passwordErrorLabel.setVisible(password.isEmpty());

		if (nameErrorLabel.isVisible() || emailErrorLabel.isVisible() || passwordErrorLabel.isVisible())
			return false;

		String result = controller.signUpUser(name, email, password);
		if (result.equalsIgnoreCase("SUCCESS")) {
			signUpErrorLabel.setVisible(false);
			ViewNavigator.loadScene("Warning List", ViewNavigator.MAIN_SCENE);
			return true;
		}
		signUpErrorLabel.setText(result);
		signUpErrorLabel.setVisible(true);
		return false;
	}

	@FXML
	public Object loadSignIn()
	{
		ViewNavigator.loadScene("Sign In", ViewNavigator.SIGN_IN_SCENE);
		return this;
	}

}
