package com.wetrack.ikongtiao.domain.machine;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by zhanghong on 16/12/5.
 */
@Entity(name = "machine_unit_value")
@Data
public class MachineUnitValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    //机组id
    @Column(name = "unit_id")
    Long unitId;
    @Column(name = "field_id")
    Long fieldId;
    @Column(length = 50)
    String value;
}
