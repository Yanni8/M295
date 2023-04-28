package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
public class Test {

    @Id
    @DBPrefer
    @GeneratedValue
    @JsonView(View.Metadata.class)
    private Long id;

    @NotEmpty
    @JsonView(View.Metadata.class)
    private String title;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Question> questions;

    @ManyToMany
    @JoinTable(
            name = "group_tests",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @JsonView(View.Hide.class)
    private Set<Group> groups;

    @ManyToMany
    @JoinTable(
            name = "user_tests",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @JsonView(View.Hide.class)
    private Set<User> users;


    @JsonIgnore
    public void setGroup(Group group) {
        if (this.groups == null) {
            this.groups = new HashSet<Group>();
        }
        this.groups.add(group);
    }

    @JsonIgnore
    public void setUser(User user) {
        if (this.users == null) {
            this.users = new HashSet<User>();
        }
        this.users.add(user);
    }
}
