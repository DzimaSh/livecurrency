package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "live_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user")
    private List<Request> requests;
    private Long chatId;
    private Long permissions;

    public String prepareUserName() {
        return (this.getUserName() != null) ? this.getUserName() :
                String.format("%s %s", this.getLastName(), this.getFirstName());
    }
}
