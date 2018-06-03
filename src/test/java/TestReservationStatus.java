import junit.framework.TestCase;
import model.dto.DonationScheduleStatusDTO;
import org.springframework.context.ApplicationContext;
import service.LoginService;
import service.TCPService;
import utils.CommonUtils;

public class TestReservationStatus extends TestCase {
    private TCPService service;
    private ApplicationContext context;
    private String status;
    private LoginService login;
    public void setUp(){
        this.context=CommonUtils.getFactory();
        this.service = this.context.getBean(TCPService.class);
        this.login=this.context.getBean(LoginService.class);
        login.handleLogin("tcp","tcp");
        //this.status=service.getAllDonationStatus().get(service.getAllDonationStatus().size()-1).getStatus();
        this.status=service.getAllDonationStatus().get(1).getStatus();
        System.out.println(this.status);
    }

    public void testUp(){
        String stat = "ACCEPTED";
        assertEquals("Success",service.handleModifyDonation(stat,2));
        assertEquals("ACCEPTED",service.getAllDonationStatus().get(service.getAllDonationStatus().size()-1).getStatus());

    }
}
