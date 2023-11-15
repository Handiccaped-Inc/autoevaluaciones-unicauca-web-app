package co.unicauca.edu.autoevaluacioneswebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabourType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "labour_type")
    private Long id;
    private String code;
    private String description;

} 
