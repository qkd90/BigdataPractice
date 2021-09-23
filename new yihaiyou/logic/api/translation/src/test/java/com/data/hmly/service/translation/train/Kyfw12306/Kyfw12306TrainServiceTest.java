package com.data.hmly.service.translation.train.Kyfw12306;

import com.data.hmly.service.translation.train.Kyfw12306.entity.LeftTickets;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Sane on 16/4/7.
 */
@Ignore
public class Kyfw12306TrainServiceTest {

    @Test
    public void testSearchTickets() throws Exception {
        List<LeftTickets.DataEntity> tickets = Kyfw12306TrainService.searchTickets("2016-04-19", "XMS", "QDK");
        for (LeftTickets.DataEntity ticket : tickets) {
            System.err.println("\t\t" + ticket.getQueryLeftNewDTO().getStation_train_code());
        }

        System.err.println("-----------");

//

    }

}