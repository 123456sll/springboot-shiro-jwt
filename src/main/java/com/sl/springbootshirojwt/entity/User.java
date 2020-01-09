package com.sl.springbootshirojwt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帐号
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @Length(min = 6, message = "密码长度不能低于6位") @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    /**
     * 注册时间
     */
    private LocalDateTime regTime;


}
