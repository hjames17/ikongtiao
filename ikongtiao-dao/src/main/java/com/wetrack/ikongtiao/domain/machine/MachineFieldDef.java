package com.wetrack.ikongtiao.domain.machine;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by zhanghong on 16/12/5.
 */
@Entity(name = "machine_field_def")
@Data
public class MachineFieldDef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(length = 50)
    String name;
}
