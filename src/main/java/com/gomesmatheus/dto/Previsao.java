package com.gomesmatheus.dto;

public class Previsao {

    private String dia;
    private String tempo;
    private long maxima;
    private long minima;
    private double iuv;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public long getMaxima() {
        return maxima;
    }

    public void setMaxima(long maxima) {
        this.maxima = maxima;
    }

    public long getMinima() {
        return minima;
    }

    public void setMinima(long minima) {
        this.minima = minima;
    }

    public double getIuv() {
        return iuv;
    }

    public void setIuv(double iuv) {
        this.iuv = iuv;
    }
}
