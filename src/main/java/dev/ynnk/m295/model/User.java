package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.validation.Create;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "user_model")
public class User {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;

    @NotNull
    @NotEmpty(groups = Create.class)
    String username;

    @NotNull
    @NotEmpty(groups = Create.class)
    String firstName;

    @NotNull
    @NotEmpty(groups = Create.class)
    String lastName;


    @JsonGetter
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }
}
