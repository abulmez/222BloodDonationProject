import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import service.LoginService;
import utils.CommonUtils;

public class TestLogin extends TestCase {
    private LoginService loginService;
    public void setUp(){
        ApplicationContext context = CommonUtils.getFactory();
        loginService = context.getBean(LoginService.class);
    }
    public void testUp(){
        assertTrue(loginService.handleLogin("donor","donor"));
        assertFalse(loginService.handleLogin("donor","notDonor"));
        assertFalse(loginService.handleLogin(" "," "));
        assertFalse(loginService.handleLogin(" ","donor"));
        assertFalse(loginService.handleLogin("donor"," "));
    }
}
