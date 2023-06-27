package com.revo.ug.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Table(name = "computers")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Computer {

    @Id
    @Column(name = "nazwa", unique = true)
    private String name;

    @Column(name = "data_ksiegowania")
    private LocalDate postingDate;

    @Column(name = "koszt_USD")
    private double costUSD;

    @Column(name = "koszt_PLN")
    private double costPLN;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return Double.compare(computer.costUSD, costUSD) == 0 && Double.compare(computer.costPLN, costPLN) == 0 && Objects.equals(name, computer.name) && Objects.equals(postingDate, computer.postingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, postingDate, costUSD, costPLN);
    }
}
