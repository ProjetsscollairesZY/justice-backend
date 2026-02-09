package com.Justice.Justice.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "salles")
public class Salle {
    @Id
@Column(name = "salleId")
    private String salleId;

  
  
public Salle() {}

    public Salle(String salleId)
         {        this.salleId = salleId;
    }

    public String getSalleId() { return salleId; }
    public void setSalleId(String salleId) { this.salleId = salleId; }

  
}