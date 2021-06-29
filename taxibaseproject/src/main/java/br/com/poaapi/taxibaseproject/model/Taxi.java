package br.com.poaapi.taxibaseproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter @Builder
@Entity @Table(name = "PONTOS_TAXI")
@NoArgsConstructor @AllArgsConstructor
public class Taxi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Integer id;

    @JsonProperty("nome")
    @Column(name = "NOME", nullable = false)
    private String name;

    @JsonProperty("lat")
    @Column(name = "LAT", nullable = false)
    private String lat;

    @JsonProperty("lng")
    @Column(name = "LNG", nullable = false)
    private String lng;

    @JsonProperty("registro")
    @Column(name = "REG", nullable = false)
    private String time;

    public Taxi (String name, String lat, String lng){
        this.id = null;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.time = this.getUTCDate();
    }

    public Taxi(String name, String lat, String lng, String time){
        this.id = null;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    @Override
    public String toString() {
        final String convert = "Taxi[id=%d, name='%s, lat='%s', lng='%s', time='%s']";
        return String.format(convert, this.id, this.name, this.lat, this.lng, this.time);
    }

    private String getUTCDate(){
        final Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(date);
    }

}
