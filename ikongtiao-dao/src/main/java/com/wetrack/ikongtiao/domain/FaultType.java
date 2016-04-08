/**
 * 2016/04/01
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;

public class FaultType implements Serializable {
    /**
     * id:
     */
    private Integer id;

    String name;

    Integer ordinal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }
}