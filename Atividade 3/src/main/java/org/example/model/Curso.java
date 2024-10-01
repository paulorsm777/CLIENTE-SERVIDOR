package org.example.model;

public class Curso {
    public enum Area {
        EXATAS, HUMANAS, BIOLOGICAS, ARTES
    }

    private Long codigo;
    private String nome;
    private String sigla;
    private Area area;

    public Long getCodigo() { return codigo; }
    public void setCodigo(Long codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
}