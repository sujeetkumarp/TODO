/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo;

import ECUtils.GUIValidator;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import todo.bean.AppUser;
import todo.dao.UserDAO;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class AddUserFXMLController implements Initializable, FXMLConst {

    GUIValidator v1 = new GUIValidator();
    @FXML
    private TextField txtPhoneNo;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private PasswordField txtPass;
    @FXML
    private ComboBox<?> cmbRole;
    @FXML
    private ComboBox<?> cmbDept;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LinkedList roles = new LinkedList();
        roles.add("User");
        roles.add("Admin");
        cmbRole.getItems().addAll(roles);
        LinkedList dept = new LinkedList();
        dept.add("CS");
        dept.add("IT");
        dept.add("ET");
        dept.add("Mech");
        dept.add("Civil");
        cmbDept.getItems().addAll(dept);

        v1.addRequiredValidator(txtName);
        v1.addRequiredValidator(txtPass);
        v1.addPassValidator(txtPass);
        v1.addRequiredValidator(txtPhoneNo);
        v1.addPNValidator(txtPhoneNo);
        v1.addRequiredValidator(cmbDept);
        v1.addRequiredValidator(cmbRole);
    }

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(SEARCH_USER_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (v1.validateAll()) {
                    if(UserDAO.findByName(txtName.getText())!=null){
                        JOptionPane.showMessageDialog(null, "Already Exists! Try Another Name");
                        return;
                    }
                    AppUser p1 = new AppUser();
                    p1.setDepartment(cmbDept.getValue().toString());
                    p1.setPass(txtPass.getText());
                    p1.setPhoneNo(txtPhoneNo.getText());
                    p1.setRoll(cmbRole.getValue().toString());
                    p1.setUserName(txtName.getText());
                    UserDAO.insert(p1);
                    //JOptionPane.showMessageDialog(null,"Done" );
                    Stage stage = (Stage) btnBack.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource(SEARCH_USER_XML));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
