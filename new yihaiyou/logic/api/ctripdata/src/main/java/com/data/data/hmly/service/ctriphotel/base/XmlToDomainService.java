package com.data.data.hmly.service.ctriphotel.base;

import com.data.data.hmly.service.ctriphotel.info.entity.ResponseType;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * Created by Sane on 15/10/29.
 */

@Service
public class XmlToDomainService {


    public ResponseType xmlToInfo(String xml) {

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(ResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ResponseType responseType = (ResponseType) unmarshaller.unmarshal(new StringReader(xml));

            return responseType;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }


    public com.data.data.hmly.service.ctriphotel.staticinfo.entity.ResponseType xmlToStatisinfo(String xml) {

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(ResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            com.data.data.hmly.service.ctriphotel.staticinfo.entity.ResponseType responseType = (com.data.data.hmly.service.ctriphotel.staticinfo.entity.ResponseType) unmarshaller.unmarshal(new StringReader(xml));

            return responseType;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }


    public com.data.data.hmly.service.ctriphotel.priceinfo.entity.ResponseType xmlToPriceinfo(String xml) {

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(ResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            com.data.data.hmly.service.ctriphotel.priceinfo.entity.ResponseType responseType = (com.data.data.hmly.service.ctriphotel.priceinfo.entity.ResponseType) unmarshaller.unmarshal(new StringReader(xml));

            return responseType;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }

}
