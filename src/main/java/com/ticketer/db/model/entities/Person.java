package com.ticketer.db.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by akshay.kesarwan on 04/10/16.
 */
@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    private Long id;

    @Version
    @Column(name = "version")
    @JsonIgnore
    private Integer version;

    @Column(name = "name")
    private String name;
}
