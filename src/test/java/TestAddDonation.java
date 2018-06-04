import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import service.LoginService;
import service.TCPService;
import utils.CommonUtils;

public class TestAddDonation extends TestCase {
    private TCPService service;
    private ApplicationContext context;
    private Integer numberOfDonations;
    private LoginService login;
    public void setUp(){
        this.context=CommonUtils.getFactory();
        this.service = this.context.getBean(TCPService.class);
        this.login=this.context.getBean(LoginService.class);
        login.handleLogin("tcp","tcp");
        this.numberOfDonations=service.handleGetDonations().size();
    }

    public void testUp(){
        assertFalse(service.handleAddDonation("dasdas","In curs de validare","435.28","This is a test").equals("Success"));
        assertEquals(numberOfDonations.intValue(),service.handleGetDonations().size());
        assertEquals("Success",service.handleAddDonation("21321312123","In curs de validare","435.28","This is a test"));
        assertEquals(numberOfDonations+1,service.handleGetDonations().size());

    }

    public void tearDown(){

    }
}
