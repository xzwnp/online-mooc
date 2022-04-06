package com.example.crmservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * com.example.cmsservice.entity
 *
 * @author xzwnp
 * 2022/3/22
 * 11:06
 * Steps：
 */
@TableName
@Data
public class CrmBanner implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    @TableField
    private String title;
    @TableField
    private String imageUrl;
    @TableField
    private String linkUrl;
    @TableField
    private String sort;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
