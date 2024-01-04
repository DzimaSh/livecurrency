package com.livecurrency.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "live_user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true)
    private String userName;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Request> requests;

    @Column(unique = true)
    private Long chatId;

    private Long permissions;

    public String prepareUserName() {
        return (this.getUserName() != null) ? this.getUserName() :
                String.format("%s %s", this.getLastName(), this.getFirstName());
    }
}
