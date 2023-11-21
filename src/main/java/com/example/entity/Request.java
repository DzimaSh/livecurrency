package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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

    private Double initialPrice;

    @ManyToOne
    private User user;

    @ManyToOne
    private Currency currency;

    @PrePersist
    public void setInitialPrice() {
        if (this.currency != null && this.initialPrice == null) {
            this.initialPrice = this.currency.getPrice();
        }
    }

    public Double calculateSignedPercentageChange() {
        if (this.initialPrice == null || this.currency == null || this.currency.getPrice() == null) {
            throw new IllegalStateException("Initial price or current price is null");
        }

        return ((this.currency.getPrice() - this.initialPrice) / this.initialPrice) * 100;
    }

}
