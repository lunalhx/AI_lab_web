package com.ruoyi.lab_web_sys.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新闻管理对象 news
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
public class News extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 新闻时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新闻时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date newsTime;

    /** 新闻标题 */
    @Excel(name = "新闻标题")
    private String newsTitle;

    /** 新闻图片 */
    @Excel(name = "新闻图片")
    private String newsImageUrl;

    /** 新闻链接 */
    @Excel(name = "新闻链接")
    private String newsLink;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNewsTime(Date newsTime) 
    {
        this.newsTime = newsTime;
    }

    public Date getNewsTime() 
    {
        return newsTime;
    }

    public void setNewsTitle(String newsTitle) 
    {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() 
    {
        return newsTitle;
    }

    public void setNewsImageUrl(String newsImageUrl) 
    {
        this.newsImageUrl = newsImageUrl;
    }

    public String getNewsImageUrl() 
    {
        return newsImageUrl;
    }

    public void setNewsLink(String newsLink) 
    {
        this.newsLink = newsLink;
    }

    public String getNewsLink() 
    {
        return newsLink;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("newsTime", getNewsTime())
            .append("newsTitle", getNewsTitle())
            .append("newsImageUrl", getNewsImageUrl())
            .append("newsLink", getNewsLink())
            .toString();
    }
}
