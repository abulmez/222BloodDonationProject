import junit.framework.TestCase;
import model.BloodRequest;
import model.DTO.BloodRequestDTO;
import service.MedicService;
import service.TCPService;
import utils.CommonUtils;

import java.util.List;

public class TestViewCenters extends TestCase{
    private List<BloodRequestDTO> list;
    private MedicService service;
    private Integer idBD;
    public void setUp(){
        service = CommonUtils.getFactory().getBean(MedicService.class);
    }
    public void testUp(){
        assert(!service.handleVizualizare(1014).equals("Eroare la incarcarea livrarilor"));
        assert(service.handleVizualizare(-1).equals("Eroare la incarcarea livrarilor"));
    }
}
