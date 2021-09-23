import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.request.TicketSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.framework.hibernate.util.Page;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by huangpeijie on 2015-12-24,0024.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class TicketServiceTest {
    @Resource
    TicketService ticketService;

    @Test
    public void testIndexTickets() throws Exception {
        ticketService.indexTickets();
    }

    @Test
    public void testIndexTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(new Long(125));
        ticket.setTicketName("test");
        ticketService.indexTicket(ticket);
    }

    @Test
    public void testListFromSolr() throws Exception {
        TicketSearchRequest request = new TicketSearchRequest();
//        request.setScenicId(new Long(13165));
        request.setId(new Long(125));
//        request.setName("厦门");
        Page page = new Page();
        page.setPageIndex(1);
        page.setPageSize(100);
        List<TicketSolrEntity> list = ticketService.listFromSolr(request, page);
        for (TicketSolrEntity ticketSolrEntity : list) {
            System.out.println(ticketSolrEntity.getId());
            System.out.println(ticketSolrEntity.getName());
            System.out.println(ticketSolrEntity.getScenicId());
        }
    }
}
