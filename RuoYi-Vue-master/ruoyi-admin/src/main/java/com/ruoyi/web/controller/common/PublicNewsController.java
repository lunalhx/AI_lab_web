package com.ruoyi.web.controller.common;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.lab_web_sys.domain.News;
import com.ruoyi.lab_web_sys.service.INewsService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公开新闻API Controller
 * 供外部网站调用，无需认证
 * 
 * @author ruoyi
 * @date 2025-05-25
 */
@RestController
@RequestMapping("/api/public/news")
@CrossOrigin(origins = "*") // 允许跨域访问
public class PublicNewsController extends BaseController
{
    @Autowired
    private INewsService newsService;

    /**
     * 查询新闻列表（公开接口）
     */
    @GetMapping("/list")
    public AjaxResult list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "newsTitle", required = false) String newsTitle)
    {
        try {
            startPage();
            News news = new News();
            if (newsTitle != null && !newsTitle.trim().isEmpty()) {
                news.setNewsTitle(newsTitle);
            }
            List<News> list = newsService.selectNewsList(news);
            TableDataInfo dataTable = getDataTable(list);
            
            // 返回标准格式
            return AjaxResult.success("查询成功", dataTable);
        } catch (Exception e) {
            logger.error("查询新闻列表失败", e);
            return AjaxResult.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取新闻详细信息（公开接口）
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        try {
            News news = newsService.selectNewsById(id);
            if (news == null) {
                return AjaxResult.error("新闻不存在");
            }
            return AjaxResult.success("查询成功", news);
        } catch (Exception e) {
            logger.error("查询新闻详情失败", e);
            return AjaxResult.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取最新新闻列表（公开接口）
     */
    @GetMapping("/latest")
    public AjaxResult latest(@RequestParam(value = "limit", defaultValue = "5") Integer limit)
    {
        try {
            startPage();
            // 设置页面大小
            if (limit > 50) limit = 50; // 限制最大数量
            
            News news = new News();
            List<News> list = newsService.selectNewsList(news);
            
            // 只返回指定数量的最新新闻
            if (list.size() > limit) {
                list = list.subList(0, limit);
            }
            
            return AjaxResult.success("查询成功", list);
        } catch (Exception e) {
            logger.error("查询最新新闻失败", e);
            return AjaxResult.error("查询失败：" + e.getMessage());
        }
    }
} 