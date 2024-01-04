package com.livecurrency.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private Double initialPrice;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    private Currency currency;

    @PrePersist
    public void setInitialPrice() {
        if (this.currency != null && this.initialPrice == null) {
            this.initialPrice = this.currency.getPrice();
        }
    }
}
