package com.innovationpassport.backend;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
