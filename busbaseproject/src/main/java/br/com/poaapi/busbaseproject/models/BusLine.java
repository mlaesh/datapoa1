package br.com.poaapi.busbaseproject.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @EqualsAndHashCode
@Entity @Table(name = "LINHAS_ONIBUS")
public class BusLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("codigo")
    private String code;

    @JsonProperty("nome")
    private String name;

    @Singular @ElementCollection
    @JsonProperty("coordenadas")
    private List<Double[]> coordinates;

    @ElementCollection
    private List<BusLine> busLines;


}
