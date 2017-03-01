package com.wetrack.ikongtiao.domain.machine;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by zhanghong on 16/12/5.
 */
@Entity(name = "plate_template")
@Data
public class PlateTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "type_id")
    Long typeId;
    @Column(length = 50)
    String title;
    @Column(name = "template_html", length = 1000)
    String templateHtml;
}
