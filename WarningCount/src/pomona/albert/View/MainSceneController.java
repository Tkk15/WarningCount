package pomona.albert.View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pomona.albert.Controller.Controller;
import pomona.albert.model.PersonBeingWarned;

public class MainSceneController implements Initializable{
	private static Controller controller = Controller.getInstance();
	@FXML
	private TextField ID;
	@FXML
	private TextField license;
	@FXML
	private ListView<PersonBeingWarned> personListView = new ListView<>();;
	@FXML
	private Button deletePerson;
	@FXML
	private TextArea description;
	PersonBeingWarned selectedPerson = personListView.getSelectionModel().getSelectedItem();
	
	
	// Event Listener on Button.onAction
	@FXML
	public boolean addPerson(ActionEvent event) {
			
		
		if(controller.addPerson(ID.getText(), license.getText(), description.getText()) == true)
		{
			clear();
			return true;
		}
			return false;
	}
	public void clear(){
		ID.clear();
		license.clear();
		description.clear();
	}
	// Event Listener on Button[#deletePerson].onAction
	@FXML
	public void deletePerson(ActionEvent event) {
		 controller.deletePerson(personListView.getSelectionModel().getSelectedItem());
	   //     deletePerson.setDisable(true);
		 personListView.getSelectionModel().clearSelection();
	        personListView.getSelectionModel().select(-1);
	     //  personListView.setItems(controller.getAllWarningsList());
	        
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		personListView.setItems(controller.getAllWarningsList());
		
	}
}
