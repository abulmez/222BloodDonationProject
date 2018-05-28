package viewController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.DonationReport;
import model.Donor;
import org.springframework.context.ApplicationContext;
import utils.MailSender;
import service.TCPService;
import utils.CommonUtils;
import utils.DonationDTO;

public class DonationsReportController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea textArea;

    @FXML
    private CheckBox emailCheckBox;

    private TCPService service;
    ApplicationContext context = CommonUtils.getFactory();
    private String status;
    private DonationDTO donation;


    public void initialize(){
        service = context.getBean(TCPService.class);
    }

    public void initData(String status, DonationDTO donation){
        this.status=status;
        this.donation=donation;
    }

    public void handleAdd(ActionEvent event){
        String errors="";
        if (datePicker.getValue()==null){
            errors+="Selectati data!\n";
        }
        if (textArea.getText().isEmpty()){
            errors+="Nu observati nimic?\n";
        }
        if (errors.equals("")){
            Boolean validitate;
            if (status.equals("In curs de validare") || status.equals("Proba invalida"))
                validitate=false;
            else
                validitate=true;
            DonationReport report=new DonationReport(donation.getIdD(),datePicker.getValue(),validitate,textArea.getText());
            String response=service.handleAddDonationReport(report);
            if (!response.equals("Success")){
                Alert alert=new Alert(Alert.AlertType.ERROR,response);
                alert.showAndWait();
            }
            else{
                if (emailCheckBox.isSelected()){
                    Donor donor=service.handleGetDonorFromDonation(donation.getIdD());
                    if (donor!=null){
                        String intro="Donatia cu numarul "+donation.getIdD()+" din data "+datePicker.getValue()+":\n";
                        String content="Detalii pacient:\n" +
                                "\tNume:"+donor.getName()+";\n"+
                                "\tCNP:"+donor.getCnp()+";\n"+
                                "\tGrupa sanguina:"+donor.getBloodGroup()+";\n"+
                                "\tData nasterii:"+donor.getBirthday()+";\n"+
                                "\tGreutate:"+donor.getWeight()+".\n"+
                                "Observatii:\n"+textArea.getText();
                        String end="\nDoneaza sange, salveaza o viata!";
                        String mesaj=intro+content+end;
                        MailSender mailSender = new MailSender(donor.getMail(), "Rezultat analize donare", mesaj);
                        Thread th = new Thread(mailSender);
                        th.setDaemon(true);
                        mailSender.setOnSucceeded((Event action)->{
                            Alert alert=new Alert(Alert.AlertType.INFORMATION,"E-mailul a fost trimis cu succes!");
                            alert.showAndWait();
                        });
                        mailSender.setOnFailed((Event action) -> {
                            Alert alert=new Alert(Alert.AlertType.ERROR,"Eroare la trimiterea e-mailului.");
                            alert.showAndWait();
                        });
                        th.start();
                    }
                    else{
                        Alert alert=new Alert(Alert.AlertType.ERROR,"Eroare la cautarea donatorului.");
                        alert.showAndWait();
                    }
                }
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Raportul a fost adaugat cu succes!");
                alert.showAndWait();
            }
            Stage thisStage=(Stage)textArea.getScene().getWindow();
            thisStage.close();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR,errors);
            alert.showAndWait();
        }

    }

}
