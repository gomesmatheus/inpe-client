package com.gomesmatheus.services;

import com.gomesmatheus.dto.Cidades;
import com.gomesmatheus.dto.ResultadoPrevisaoTotal;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Service
public class XmlParser {

    public Cidades xmlToListaDeCidades(String xml) {
        Cidades cidades = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Cidades.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            cidades = (Cidades) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return cidades;
    }

    public ResultadoPrevisaoTotal xmlToPrevisaoDoTempo(String xml) {
        ResultadoPrevisaoTotal previsaoTotal = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ResultadoPrevisaoTotal.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            previsaoTotal = (ResultadoPrevisaoTotal) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return previsaoTotal;
    }
}
