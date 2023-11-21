package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseEntity {

    @Column(unique = true)
    private String symbol;

    private Double price;

    @OneToMany(mappedBy = "currency")
    private List<Request> request;
}
