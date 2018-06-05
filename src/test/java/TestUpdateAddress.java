import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import utils.CommonUtils;

public class TestUpdateAddress extends TestCase {
    public void setUp(){

    }

    public void testUp(){
        DonorService service;
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(DonorService.class);
        service.handleAdress("Avram Iancu","23","2","B","4","33","Cluj-Napoca","Cluj","Romania","1045");
        String response=service.handleFields("1045");
        String[] data = response.split("&");
        assertEquals("Avram Iancu",data[9].split("=")[1]);
        assertEquals("23",data[10].split("=")[1]);
        service.handleAdress("Grigore Alexandrescu","41","2","B","4","33","Cluj-Napoca","Cluj","Romania","1045");
    }
}
