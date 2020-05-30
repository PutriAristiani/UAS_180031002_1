package com.bi183.aristiani;

import java.util.Date;

public class Film {
    private int idFilm;
    private String judul;
    private String genre;
    private String episode;
    private Date tanggal_rilis;
    private String penulis_skenario;
    private String sinopsis;
    private String gambar;
    private String link;

    public Film(int idFilm, String judul, String genre, String episode, Date tanggal_rilis, String penulis_skenario, String sinopsis, String gambar, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.genre = genre;
        this.episode = episode;
        this.tanggal_rilis = tanggal_rilis;
        this.penulis_skenario = penulis_skenario;
        this.sinopsis = sinopsis;
        this.gambar = gambar;
        this.link = link;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Date getTanggalRilis() {
        return tanggal_rilis;
    }

    public void setTanggalRilis(Date tanggal_rilis) {
        this.tanggal_rilis = tanggal_rilis;
    }

    public String getPenulisSkenario() {
        return penulis_skenario;
    }

    public void setPenulisSkenario(String penulis_skenario) {
        this.penulis_skenario = penulis_skenario;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }
}
