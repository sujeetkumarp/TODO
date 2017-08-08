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
import javafx.concurrent.Task;
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
import static todo.FXMLConst.*;
import todo.bean.MyTask;
import todo.dao.TaskDAO;
import todo.dao.UserDAO;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class UpdateTaskFXMLController implements Initializable {
    
    static String id = "";    
    GUIValidator v1 = new GUIValidator();
    @FXML
    private TextField txtDueDate;
    @FXML
    private TextArea taMsg;
    @FXML
    private ComboBox<?> cmbCrFor;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private ComboBox<?> cmbStatus;
    @FXML
    private ComboBox<?> cmbPriority;
    @FXML
    private TextArea taMsg1;
    
    
     public void populate(){
         MyTask s1 = TaskDAO.findById(id);
         taMsg.setText(s1.getMsg());
         txtDueDate.setText(MyUtils.dateToStr(s1.getDueDate()));
         MyUtils.selectComboBoxValue(cmbCrFor, s1.getCreatedFor());
         MyUtils.selectComboBoxValue(cmbPriority, s1.getPriority()+"");
         MyUtils.selectComboBoxValue(cmbStatus, s1.getStatus());
    }
    
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
        
        populate();        
        
        if(!"admin".equalsIgnoreCase(ToDoFXMLController.u1.getRoll())){
            cmbCrFor.setDisable(true);
            cmbPriority.setDisable(true);
            txtDueDate.setDisable(true);
        }
    }    

    @FXML
    private void he(ActionEvent event) {
       if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(ADMIN_XML));
                    if(!"admin".equalsIgnoreCase(ToDoFXMLController.u1.getRoll())){
                     root = FXMLLoader.load(getClass().getResource(USER_XML));
                    }
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
                    MyTask p1 = TaskDAO.findById(id);
                    p1.setCreatedBy(ToDoFXMLController.u1.getUserName());
                    //p1.setCreatedDate(MyUtils.getToday());
                    p1.setCreatedFor(cmbCrFor.getValue().toString());
                    p1.setDueDate(MyUtils.getDate(txtDueDate.getText()));
                    if(taMsg1.getText()!=null && !"".equals(taMsg1.getText())){
                        p1.setMsg(taMsg.getText() +"\n-----------------------\n" +  ToDoFXMLController.u1.getUserName()+ " : " + taMsg1.getText());                        
                    }
                    p1.setId(id);
                    int pri = Integer.parseInt(cmbPriority.getValue().toString());
                    p1.setPriority(pri);
                    p1.setStatus(cmbStatus.getValue().toString());
                    TaskDAO.update(p1);
                    //JOptionPane.showMessageDialog(null,"Done" );
                    Stage stage = (Stage) btnBack.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource(ADMIN_XML));
                    if(!"admin".equalsIgnoreCase(ToDoFXMLController.u1.getRoll())){
                     root = FXMLLoader.load(getClass().getResource(USER_XML));
                    }
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
