package com.gomesmatheus.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cidade")
public class ResultadoPrevisaoTotal {

    private String nome;
    private String uf;
    private String atualizacao;

    private List<Previsao> previsao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(String atualizacao) {
        this.atualizacao = atualizacao;
    }

    public List<Previsao> getPrevisao() {
        return previsao;
    }

    public void setPrevisao(List<Previsao> previsao) {
        this.previsao = previsao;
    }
}
