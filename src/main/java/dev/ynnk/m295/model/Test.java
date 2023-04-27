package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    @Id
    @DBPrefer
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Question> questions;

    @ManyToMany
    @JoinTable(
            name = "group_tests",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> groups;

    @ManyToMany
    @JoinTable(
            name = "user_tests",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
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
