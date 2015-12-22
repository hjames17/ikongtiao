/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: Sequence.java
 * Author: xiazhou
 * Date: 2015年03月27日 下午 03:04:24
 * Description:
 */
package com.wetrack.ikongtiao.domain;

public class Sequence {
    /**
     * name:序列名称
     */
    private String name;

    /**
     * current_value:序列当前值
     */
    private Long currentValue;

    /**
     * increment:自增值
     */
    private Integer increment;

    /**
     * min_value:最小值
     */
    private Integer minValue;

    /**
     * max_value:最大值
     */
    private Long maxValue;

    /**
     * cycle:是否循环:0-否,1-是
     */
    private Integer cycle;

    /**
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value for name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return the value of current_value
     */
    public Long getCurrentValue() {
        return currentValue;
    }

    /**
     * @param currentValue the value for current_value
     */
    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * @return the value of increment
     */
    public Integer getIncrement() {
        return increment;
    }

    /**
     * @param increment the value for increment
     */
    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    /**
     * @return the value of min_value
     */
    public Integer getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the value for min_value
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the value of max_value
     */
    public Long getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the value for max_value
     */
    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the value of cycle
     */
    public Integer getCycle() {
        return cycle;
    }

    /**
     * @param cycle the value for cycle
     */
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }
}