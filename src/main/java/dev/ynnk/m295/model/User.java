package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;

    @NotNull
    @NotEmpty(groups = Create.class)
    @Column(unique = true)
    @JsonView(View.Metadata.class)
    private String username;

    @NotNull
    @NotEmpty(groups = Create.class)
    @JsonView(View.Metadata.class)
    private String firstName;

    @NotNull
    @NotEmpty(groups = Create.class)
    @JsonView(View.Metadata.class)
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
