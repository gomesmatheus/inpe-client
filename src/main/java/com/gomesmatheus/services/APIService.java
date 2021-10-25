package com.gomesmatheus.services;

import com.gomesmatheus.dto.Cidade;
import com.gomesmatheus.dto.Cidades;
import com.gomesmatheus.dto.ResultadoPrevisaoTotal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class APIService {
    static final Map<String, String> map = Map.ofEntries(
            Map.entry("ec", "Encoberto com Chuvas Isoladas"),
            Map.entry("ci", "Chuvas Isoladas"),
            Map.entry("c", "Chuva"),
            Map.entry("in", "Instável"),
            Map.entry("pp", "Poss. de Pancadas de Chuva"),
            Map.entry("cm", "Chuva pela Manhã"),
            Map.entry("cn", "Chuva a Noite"),
            Map.entry("pt", "Pancadas de Chuva a Tarde"),
            Map.entry("pm", "Pancadas de Chuva pela Manhã"),
            Map.entry("np", "Nublado e Pancadas de Chuva"),
            Map.entry("pc", "Pancadas de Chuva"),
            Map.entry("pn", "Parcialmente Nublado"),
            Map.entry("cv", "Chuvisco"),
            Map.entry("ch", "Chuvoso"),
            Map.entry("t", "Tempestade"),
            Map.entry("ps", "Predomínio de Sol"),
            Map.entry("e", "Encoberto"),
            Map.entry("n", "Nublado"),
            Map.entry("cl", "Céu Claro"),
            Map.entry("nv", "Nevoeiro"),
            Map.entry("g", "Geada"),
            Map.entry("ne", "Neve"),
            Map.entry("nd", "Não Definido"),
            Map.entry("pnt", "Pancadas de Chuva a Noite"),
            Map.entry("psc", "Possibilidade de Chuva"),
            Map.entry("pcm", "Possibilidade de Chuva pela Manhã"),
            Map.entry("pct", "Possibilidade de Chuva a Tarde"),
            Map.entry("pcn", "Possibilidade de Chuva a Noite"),
            Map.entry("npt", "Nublado com Pancadas a Tarde"),
            Map.entry("npn", "Nublado com Pancadas a Noite"),
            Map.entry("ncn", "Nublado com Poss. de Chuva a Noite"),
            Map.entry("nct", "Nublado com Poss. de Chuva a Tarde"),
            Map.entry("ncm", "Nubl. c/ Poss. de Chuva pela Manhã"),
            Map.entry("npm", "Nublado com Pancadas pela Manhã"),
            Map.entry("npp", "Nublado com Possibilidade de Chuva"),
            Map.entry("vn", "Variação de Nebulosidade"),
            Map.entry("ct", "Chuva a Tarde"),
            Map.entry("ppn", "Poss. de Panc. de Chuva a Noite"),
            Map.entry("ppt", "Poss. de Panc. de Chuva a Tarde"),
            Map.entry("ppm", "Poss. de Panc. de Chuva pela Manhã"));

    private final HttpClient client;
    private final XmlParser xmlParser;


    @Autowired
    public APIService(){
        client = HttpClient.newHttpClient();
        xmlParser = new XmlParser();
    }

    public List<Cidade> getListaDeCidades() {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://servicos.cptec.inpe.br/XML/listaCidades")).build();

        String responseBody = "";
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Cidades cidades = xmlParser.xmlToListaDeCidades(responseBody);
        return cidades.getCidade();
    }

    public ResultadoPrevisaoTotal getPrevisaoDoTempo(String nomeDaCidade) {
        String nomeDaCidadeSemAcento = StringUtils.stripAccents(nomeDaCidade).replaceAll(" ", "%20");
        HttpRequest requestCidade = HttpRequest.newBuilder(URI.create("http://servicos.cptec.inpe.br/XML/listaCidades?city=" + nomeDaCidadeSemAcento)).build();

        String responseCidades = "";
        try {
            HttpResponse<String> response = client.send(requestCidade, HttpResponse.BodyHandlers.ofString());
            responseCidades = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Cidades cidades = xmlParser.xmlToListaDeCidades(responseCidades);
        Cidade cidade = cidades.getCidade().get(0);

        HttpRequest requestPrevisaoDoTempo = HttpRequest.newBuilder(URI.create("http://servicos.cptec.inpe.br/XML/cidade/" + cidade.getId() + "/previsao.xml")).build();
        String responsePrevisao = "";
        try {
            HttpResponse<String> response = client.send(requestPrevisaoDoTempo, HttpResponse.BodyHandlers.ofString());
            responsePrevisao = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ResultadoPrevisaoTotal resultadoPrevisaoTotal = xmlParser.xmlToPrevisaoDoTempo(responsePrevisao);
        return parseFields(resultadoPrevisaoTotal);
    }

    private ResultadoPrevisaoTotal parseFields(ResultadoPrevisaoTotal resultadoPrevisaoTotal) {
        LocalDate localDate = LocalDate.parse(resultadoPrevisaoTotal.getAtualizacao());
        resultadoPrevisaoTotal.setAtualizacao(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        resultadoPrevisaoTotal.getPrevisao().forEach(p -> {
            LocalDate date = LocalDate.parse(p.getDia());
            p.setTempo(map.get(p.getTempo()));
            p.setDia(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        return resultadoPrevisaoTotal;
    }


}
