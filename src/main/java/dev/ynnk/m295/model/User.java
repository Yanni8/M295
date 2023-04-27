package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.validation.Create;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user_model")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;

    @NotNull
    @NotEmpty(groups = Create.class)
    @Column(unique = true)
    private String username;

    @NotNull
    @NotEmpty(groups = Create.class)
    private String firstName;

    @NotNull
    @NotEmpty(groups = Create.class)
    private String lastName;


    @JsonGetter
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }


}
