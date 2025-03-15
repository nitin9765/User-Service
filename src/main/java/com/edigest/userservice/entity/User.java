package com.edigest.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "users")
public class User {

    @Id
    private String userId;

    private String name;

    private String phoneNumber;

    private String email;

    @Override
    public String toString() {
        return this.userId+" "+ this.name+" "+this.phoneNumber+" "+this.email;
    }
}
