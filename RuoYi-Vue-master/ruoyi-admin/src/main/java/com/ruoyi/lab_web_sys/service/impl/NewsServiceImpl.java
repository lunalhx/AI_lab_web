package com.ruoyi.lab_web_sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lab_web_sys.mapper.NewsMapper;
import com.ruoyi.lab_web_sys.domain.News;
import com.ruoyi.lab_web_sys.service.INewsService;

/**
 * 新闻管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@Service
public class NewsServiceImpl implements INewsService 
{
    @Autowired
    private NewsMapper newsMapper;

    /**
     * 查询新闻管理
     * 
     * @param id 新闻管理主键
     * @return 新闻管理
     */
    @Override
    public News selectNewsById(Long id)
    {
        return newsMapper.selectNewsById(id);
    }

    /**
     * 查询新闻管理列表
     * 
     * @param news 新闻管理
     * @return 新闻管理
     */
    @Override
    public List<News> selectNewsList(News news)
    {
        return newsMapper.selectNewsList(news);
    }

    /**
     * 新增新闻管理
     * 
     * @param news 新闻管理
     * @return 结果
     */
    @Override
    public int insertNews(News news)
    {
        return newsMapper.insertNews(news);
    }

    /**
     * 修改新闻管理
     * 
     * @param news 新闻管理
     * @return 结果
     */
    @Override
    public int updateNews(News news)
    {
        return newsMapper.updateNews(news);
    }

    /**
     * 批量删除新闻管理
     * 
     * @param ids 需要删除的新闻管理主键
     * @return 结果
     */
    @Override
    public int deleteNewsByIds(Long[] ids)
    {
        return newsMapper.deleteNewsByIds(ids);
    }

    /**
     * 删除新闻管理信息
     * 
     * @param id 新闻管理主键
     * @return 结果
     */
    @Override
    public int deleteNewsById(Long id)
    {
        return newsMapper.deleteNewsById(id);
    }
}
