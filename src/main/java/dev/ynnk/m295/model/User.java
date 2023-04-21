package dev.ynnk.m295.model;

import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;

    @NotNull
    @NotEmpty
    String username;

    @NotNull
    @NotEmpty
    String firstName;

    @NotNull
    @NotEmpty
    String lastName;

}
