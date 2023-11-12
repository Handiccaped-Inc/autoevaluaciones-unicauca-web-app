package co.unicauca.edu.autoevaluacioneswebapp.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class userRoleKey implements Serializable {

    @Column(name = "user_id ")
    Long userId;

    @Column(name = "role_id")
    long roleId;
    
}
