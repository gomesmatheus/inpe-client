package com.gomesmatheus.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cidades")
public class Cidades {

    private List<Cidade> cidade;

    public List<Cidade> getCidade() {
        return cidade;
    }
    public void setCidade(List<Cidade> cidade) {
        this.cidade = cidade;
    }
}
