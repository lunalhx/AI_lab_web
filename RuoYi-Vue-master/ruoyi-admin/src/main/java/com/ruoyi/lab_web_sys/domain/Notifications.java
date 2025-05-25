package com.ruoyi.lab_web_sys.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 通知管理对象 notifications
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
public class Notifications extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 通知发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通知发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date notificationTime;

    /** 通知标题 */
    @Excel(name = "通知标题")
    private String notificationTitle;

    /** 通知详细内容 */
    @Excel(name = "通知详细内容")
    private String notificationContent;

    /** 通知相关文件的URL或路径，可为空 */
    @Excel(name = "通知相关文件的URL或路径，可为空")
    private String notificationFileUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNotificationTime(Date notificationTime) 
    {
        this.notificationTime = notificationTime;
    }

    public Date getNotificationTime() 
    {
        return notificationTime;
    }

    public void setNotificationTitle(String notificationTitle) 
    {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationTitle() 
    {
        return notificationTitle;
    }

    public void setNotificationContent(String notificationContent) 
    {
        this.notificationContent = notificationContent;
    }

    public String getNotificationContent() 
    {
        return notificationContent;
    }

    public void setNotificationFileUrl(String notificationFileUrl) 
    {
        this.notificationFileUrl = notificationFileUrl;
    }

    public String getNotificationFileUrl() 
    {
        return notificationFileUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("notificationTime", getNotificationTime())
            .append("notificationTitle", getNotificationTitle())
            .append("notificationContent", getNotificationContent())
            .append("notificationFileUrl", getNotificationFileUrl())
            .toString();
    }
}
