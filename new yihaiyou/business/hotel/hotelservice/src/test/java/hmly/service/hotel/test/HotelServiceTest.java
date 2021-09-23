package hmly.service.hotel.test;

import com.data.data.hmly.service.common.AreaRelationService;
import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.hotel.HotelCtripService;
import com.data.data.hmly.service.hotel.UpdateCtripHotelService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vacuity on 15/12/31.
 */
@Ignore
public class HotelServiceTest {


    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
    private final UpdateCtripHotelService updateCtripHotelService = (UpdateCtripHotelService) applicationContext.getBean("updateCtripHotelService");
    private final HotelCtripService hotelCtripService = (HotelCtripService) applicationContext.getBean("hotelCtripService");
    private final AreaRelationService areaRelationService = (AreaRelationService) applicationContext.getBean("areaRelationService");

    public void atestUpdate() {
        Long areaId = 350200L;
        updateCtripHotelService.update(areaId);
    }

    public void atestHotelUpdate() {
        Long areaId = 350200L;
        AreaRelation areaRelation = areaRelationService.get(areaId);
        hotelCtripService.doCtripToLxbHotel(areaRelation);
    }

    @Test
    public void testCtripUpdate() {
        //
//        updateCtripHotelService.ctripUpdate();
    }
}
