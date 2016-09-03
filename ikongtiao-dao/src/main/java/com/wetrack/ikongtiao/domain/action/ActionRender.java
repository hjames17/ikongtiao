package com.wetrack.ikongtiao.domain.action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghong on 16/8/16.
 */
public class ActionRender {

    static String build(Action action){
        String template = action.getTemplate();

        List<String> keys = new ArrayList<>();

        String formatString = parseTemplate(template, keys);

        return String.format(formatString, "");

    }

    static String parseTemplate(String template, List<String> keys){
        return template;
    }



}
