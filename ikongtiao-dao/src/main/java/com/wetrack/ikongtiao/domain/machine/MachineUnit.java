package com.wetrack.ikongtiao.domain.machine;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhanghong on 16/12/5.
 */
@Deprecated
@Entity(name = "machine_unit")
@Data
public class MachineUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "type_id")
    Long typeId;
    @Column(name = "plate_template_id")
    Long plateTemplateId;
    //制造编号，制令号
    @Column(name = "build_no", length = 50)
    String buildNo;
    @Column(name = "build_time")
    Date buildTime;
    //制令编号二维码图片
    @Column(name = "qrCode_img")
    String qrCodeImg;
    @Column(name = "model_name", length = 50)
    String modelName;
    @Column(name = "type_name", length = 50)
    String typeName;
    @Column(length = 1000)
    String plate;

}
