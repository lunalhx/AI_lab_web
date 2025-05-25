package com.ruoyi.lab_web_sys.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动管理对象 activities
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
public class Activities extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 活动开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activityTime;

    /** 活动标题 */
    @Excel(name = "活动标题")
    private String activityTitle;

    /** 活动地点 */
    @Excel(name = "活动地点")
    private String activityLocation;

    /** 主持人 */
    @Excel(name = "主持人")
    private String activityHost;

    /** 记录人 */
    @Excel(name = "记录人")
    private String activityRecorder;

    /** 参会人员名单 (例如：张三,李四,王五) */
    @Excel(name = "参会人员名单 (例如：张三,李四,王五)")
    private String activityAttendees;

    /** 活动详细内容 */
    @Excel(name = "活动详细内容")
    private String activityContent;

    /** 活动图片链接1 */
    @Excel(name = "活动图片链接1")
    private String activityImageUrl1;

    /** 活动图片链接2 */
    @Excel(name = "活动图片链接2")
    private String activityImageUrl2;

    /** 活动图片链接3 */
    @Excel(name = "活动图片链接3")
    private String activityImageUrl3;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setActivityTime(Date activityTime) 
    {
        this.activityTime = activityTime;
    }

    public Date getActivityTime() 
    {
        return activityTime;
    }

    public void setActivityTitle(String activityTitle) 
    {
        this.activityTitle = activityTitle;
    }

    public String getActivityTitle() 
    {
        return activityTitle;
    }

    public void setActivityLocation(String activityLocation) 
    {
        this.activityLocation = activityLocation;
    }

    public String getActivityLocation() 
    {
        return activityLocation;
    }

    public void setActivityHost(String activityHost) 
    {
        this.activityHost = activityHost;
    }

    public String getActivityHost() 
    {
        return activityHost;
    }

    public void setActivityRecorder(String activityRecorder) 
    {
        this.activityRecorder = activityRecorder;
    }

    public String getActivityRecorder() 
    {
        return activityRecorder;
    }

    public void setActivityAttendees(String activityAttendees) 
    {
        this.activityAttendees = activityAttendees;
    }

    public String getActivityAttendees() 
    {
        return activityAttendees;
    }

    public void setActivityContent(String activityContent) 
    {
        this.activityContent = activityContent;
    }

    public String getActivityContent() 
    {
        return activityContent;
    }

    public void setActivityImageUrl1(String activityImageUrl1) 
    {
        this.activityImageUrl1 = activityImageUrl1;
    }

    public String getActivityImageUrl1() 
    {
        return activityImageUrl1;
    }

    public void setActivityImageUrl2(String activityImageUrl2) 
    {
        this.activityImageUrl2 = activityImageUrl2;
    }

    public String getActivityImageUrl2() 
    {
        return activityImageUrl2;
    }

    public void setActivityImageUrl3(String activityImageUrl3) 
    {
        this.activityImageUrl3 = activityImageUrl3;
    }

    public String getActivityImageUrl3() 
    {
        return activityImageUrl3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityTime", getActivityTime())
            .append("activityTitle", getActivityTitle())
            .append("activityLocation", getActivityLocation())
            .append("activityHost", getActivityHost())
            .append("activityRecorder", getActivityRecorder())
            .append("activityAttendees", getActivityAttendees())
            .append("activityContent", getActivityContent())
            .append("activityImageUrl1", getActivityImageUrl1())
            .append("activityImageUrl2", getActivityImageUrl2())
            .append("activityImageUrl3", getActivityImageUrl3())
            .toString();
    }
}
