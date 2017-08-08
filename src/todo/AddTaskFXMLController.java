/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo;

import ECUtils.GUIValidator;
import ECUtils.MyUtils;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static todo.FXMLConst.SEARCH_USER_XML;
import todo.bean.AppUser;
import todo.bean.MyTask;
import todo.dao.TaskDAO;
import todo.dao.UserDAO;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class AddTaskFXMLController implements Initializable, FXMLConst {
    GUIValidator v1 = new GUIValidator();
    @FXML
    private ComboBox<?> cmbPriority;
    @FXML
    private ComboBox<?> cmbStatus;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox<?> cmbCrFor;
    @FXML
    private TextArea taMsg;
    @FXML
    private TextField txtDueDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LinkedList priority = new LinkedList();
        priority.add("1");
        priority.add("2");
        priority.add("3");
        priority.add("4");
        cmbPriority.getItems().addAll(priority);
        LinkedList status = new LinkedList();
        status.add("New");
        status.add("Done");
        status.add("Pending");
        status.add("Close");
        cmbStatus.getItems().addAll(status);
        
        LinkedList res = UserDAO.getUsers();
        cmbCrFor.getItems().addAll(res);

        v1.addRequiredValidator(txtDueDate);
        v1.addDateValidator(txtDueDate);
        //v1.addRequiredValidator(taMsg);
        v1.addRequiredValidator(cmbCrFor);
        v1.addRequiredValidator(cmbPriority);    
        v1.addRequiredValidator(cmbStatus);    
    }    

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(ADMIN_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (v1.validateAll()) {
                    if(null==taMsg.getText() || "".equals(taMsg.getText())){
                        JOptionPane.showMessageDialog(null,"Msg can not be blank" );
                    return;
                    }
                    if(MyUtils.isOldDate(MyUtils.getDate(txtDueDate.getText()))){
                        JOptionPane.showMessageDialog(null,"Due Date can not old date!" );
                        return;
                    }                    
                    MyTask p1 = new MyTask();
                    p1.setCreatedBy(ToDoFXMLController.u1.getUserName());
                    p1.setCreatedDate(MyUtils.getToday());
                    p1.setCreatedFor(cmbCrFor.getValue().toString());
                    p1.setDueDate(MyUtils.getDate(txtDueDate.getText()));
                    p1.setMsg(taMsg.getText());
                    int pri = Integer.parseInt(cmbPriority.getValue().toString());
                    p1.setPriority(pri);
                    p1.setStatus(cmbStatus.getValue().toString());
                    TaskDAO.insert(p1);
                    //JOptionPane.showMessageDialog(null,"Done" );
                    Stage stage = (Stage) btnBack.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource(ADMIN_XML));
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
