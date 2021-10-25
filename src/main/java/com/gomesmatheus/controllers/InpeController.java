package com.gomesmatheus.controllers;

import com.gomesmatheus.dto.Cidade;
import com.gomesmatheus.dto.ResultadoPrevisaoTotal;
import com.gomesmatheus.services.InpeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class InpeController {

    @Autowired
    private InpeService service;

    @GetMapping("/")
    public String inpe(Model model) {
        List<Cidade> cidades = service.getListaDeCidades();
        model.addAttribute("cidades", cidades);
        return "inpe";
    }

    @PostMapping("/previsao")
    public String test(@Valid String cidade, Model model) {
        ResultadoPrevisaoTotal previsaoDoTempo = service.getPrevisaoDoTempo(cidade);
        model.addAttribute("previsao", previsaoDoTempo);
        return "previsao";
    }

}
