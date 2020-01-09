package com.sl.springbootshirojwt.common.bean;

import com.sl.springbootshirojwt.common.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
public class ResultBean<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultBean() {
        this.code= ResultCode.SUCCESS.getCode();
        this.msg="操作成功！";
    }

}
