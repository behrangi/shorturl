package com.payroc.ehsan.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "parameter")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="lvalue", columnDefinition = "integer DEFAULT 0")
    private long lvalue = 0L;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version = 0L;

    public Parameter(String name, Long value){
        version=0L;
        this.name=name;
        this.lvalue=value;
    }

    public Parameter() {

    }

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

    public long getLvalue() {
        return lvalue;
    }

    public void setLvalue(long lvalue) {
        this.lvalue = lvalue;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
