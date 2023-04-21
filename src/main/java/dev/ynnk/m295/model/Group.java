package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "group_model")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;

    @NotNull
    @NotEmpty
    private String groupName;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

    @JsonIgnore
    public void addUser(User user){
        this.users.add(user);
    }

}
