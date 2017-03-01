package com.wetrack.ikongtiao.domain.machine;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * Created by zhanghong on 16/11/30.
 */
@Entity(name = "machine_model")
@Data
public class MachineModel implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 机种名字
     */
    @Column(length = 50)
    private String name;
    /**
     * 机种描述
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 机种logo
     */
    private String logo;
}
