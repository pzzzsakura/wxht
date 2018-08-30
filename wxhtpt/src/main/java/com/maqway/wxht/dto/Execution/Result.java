package com.maqway.wxht.dto.Execution;

import com.maqway.wxht.Enums.ResultEnum;

/**
 * Ma.li.ran
 * 2017/9/23 0023.11:11
 */
public class Result<T> {

    private Integer state;//错误码

    private String stateInfo;//提示信息

    private T data;//具体内容

    private int count;//条数


    public Result(ResultEnum resultEnum,T data) {
        this.data = data;
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getStateInfo();
    }
    public Result(ResultEnum resultEnum,T data,int count) {
        this.data = data;
        this.count = count;
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getStateInfo();
    }

    public Result(ResultEnum resultEnum) {
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getStateInfo();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
