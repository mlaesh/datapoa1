package br.com.poaapi.busbaseproject.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data @Builder
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "ITINERARIO")
public class Coordinates implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idBusLine;

    private Double lat;

    private Double lng;

}

