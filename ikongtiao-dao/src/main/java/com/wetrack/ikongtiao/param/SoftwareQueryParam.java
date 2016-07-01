package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;
import lombok.Data;

/**
 * Created by zhanghong on 15/12/30.
 */
@Data
public class SoftwareQueryParam extends BaseCondition {

    String version;
    boolean released;
    String platform;

}
