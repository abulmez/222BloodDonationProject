package viewController;

import errorMessage.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;

public class AddressController {
    @FXML
    private TextField fieldStrada,fieldNr,fieldApart,fieldBloc,fieldScara,fieldEtaj,fieldOras,fieldJudet,fieldTara;

    String cnp;
    AdminService service;
    String idA;
    boolean go=true;
    String nrText="";
    String blocText="";
    String etajText="";
    String apartText="";

    public void setCnp(String cnp){
        this.cnp=cnp;
        System.out.println(cnp);
        String response=service.getAddress(cnp);
        String[] data = response.split("&");
        idA=(data[0].split("=")[1]);
        fieldStrada.setText(data[1].split("=")[1]);
        fieldNr.setText(data[2].split("=")[1]);
        fieldBloc.setText(data[3].split("=")[1]);
        fieldScara.setText(data[4].split("=")[1]);
        fieldEtaj.setText(data[5].split("=")[1]);
        fieldApart.setText(data[6].split("=")[1]);
        fieldOras.setText(data[7].split("=")[1]);
        fieldJudet.setText(data[8].split("=")[1]);
        fieldTara.setText(data[9].split("=")[1]);
    }

    public void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(AdminService.class);

    }

    @FXML
    public void handleUpdate(){
        try {
            Integer.parseInt(fieldNr.getText());
        } catch (Exception e){
            nrText="Campul nr. strada trebuie sa fie un numar\n";
            go=false;
        }

        try{
            Integer.parseInt(fieldBloc.getText());
        } catch (Exception e){
            blocText="Campul nr. bloc trebuie sa fie un numar\n";
            go=false;
        }

        try{
            Integer.parseInt(fieldEtaj.getText());
        } catch (Exception e){
            etajText="Campul etaj trebuie sa fie un numar\n";
            go=false;
        }

        try{
            Integer.parseInt(fieldApart.getText());
        } catch (Exception e){
            apartText="Campul apartament trebuie sa fie un numar\n";
            go=false;
        }

        if (fieldStrada.getText().equals("") || fieldNr.getText().equals("") || fieldBloc.getText().equals("") || fieldScara.getText().equals("") || fieldEtaj.equals("") || fieldApart.equals("") || fieldOras.equals("") || fieldJudet.equals("") || fieldTara.equals(""))
            ErrorMessage.showErrorMessage(null,"Campurile nu pot fi vide");
        else if (!go)
            ErrorMessage.showErrorMessage(null,nrText+blocText+etajText+apartText);
        else
            service.updateAdress(fieldStrada.getText(),fieldNr.getText(),fieldBloc.getText(),fieldScara.getText(),fieldEtaj.getText(),fieldApart.getText(),fieldOras.getText(),fieldJudet.getText(),fieldTara.getText(),idA);
    }
}
