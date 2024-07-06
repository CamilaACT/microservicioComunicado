package com.udla.evaluaytor.businessdomain.empresa.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Perito extends Empresa{

}
