package com.geofishing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.geofishing.controllers.UserListSerializer;
import org.hibernate.annotations.LazyToOne;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    private Long id;
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "created_by")
    private String creator;

    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
    @SequenceGenerator(sequenceName = "role_seq", allocationSize = 1, name = "ROLE_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @JsonSerialize(using = UserListSerializer.class)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


}
