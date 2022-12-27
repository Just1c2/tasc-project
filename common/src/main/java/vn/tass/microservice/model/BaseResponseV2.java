package vn.tass.microservice.model;

import lombok.Data;

import java.util.List;

@Data
public class BaseResponseV2<T> {
    private int code;
    private String message;
    private T data;
    private List<T> dataList;

    public BaseResponseV2(){
        this.code = ERROR.SUCCESS.code;
        this.message = "SUCCESS";
    }

    public boolean isSuccess(){
        return this.code == ERROR.SUCCESS.code;
    }

    public BaseResponseV2(T data) {
        this.code = ERROR.SUCCESS.code;
        this.message = "SUCCESS";
        this.data = data;
    }

    public BaseResponseV2(List<T> dataList) {
        this.code = ERROR.SUCCESS.code;
        this.message = "SUCCESS";
        this.dataList = dataList;
    }
}

