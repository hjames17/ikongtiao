package com.wetrack.ikongtiao.domain.machine;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by zhanghong on 16/12/5.
 */
@Entity(name = "machine_type_field")
@Data
public class MachineTypeField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "type_id")
    Long typeId;
    @Column(name = "field_id")
    Long fieldId;
    Boolean optional;
}
