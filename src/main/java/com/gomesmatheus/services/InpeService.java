package com.gomesmatheus.services;

import com.gomesmatheus.dto.Cidade;
import com.gomesmatheus.dto.ResultadoPrevisaoTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InpeService {

    @Autowired
    private APIService apiService;

    public List<Cidade> getListaDeCidades() {
        return apiService.getListaDeCidades();
    }

    public ResultadoPrevisaoTotal getPrevisaoDoTempo(String nomeDaCidade) {
        return apiService.getPrevisaoDoTempo(nomeDaCidade);
    }
}
