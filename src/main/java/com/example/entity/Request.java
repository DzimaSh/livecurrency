package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "request")
@NoArgsConstructor
@AllArgsConstructor
public class Request extends BaseEntity {
    private Double percents;

    private Date timeToStart;

    @ManyToOne
    private User user;

    @ManyToOne
    private Currency currency;
}
