package com.ruoyi.lab_web_sys.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.lab_web_sys.domain.Activities;
import com.ruoyi.lab_web_sys.service.IActivitiesService;
import com.ruoyi.lab_web_sys.service.ActivityPageService;

/**
 * 公开活动API控制器
 * 提供无需认证的活动接口供外部网站调用
 * 
 * @author ruoyi
 * @date 2025-05-25
 */
@RestController
@RequestMapping("/api/public/activities")
@CrossOrigin(origins = "*")
public class PublicActivitiesController extends BaseController
{
    @Autowired
    private IActivitiesService activitiesService;
    
    @Autowired
    private ActivityPageService activityPageService;

    /**
     * 获取活动列表
     */
    @GetMapping("/list")
    public AjaxResult list()
    {
        Activities activities = new Activities();
        List<Activities> list = activitiesService.selectActivitiesList(activities);
        return success(list);
    }

    /**
     * 根据ID获取活动详细信息
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(activitiesService.selectActivitiesById(id));
    }

    /**
     * 获取最新的活动
     */
    @GetMapping("/latest")
    public AjaxResult getLatest()
    {
        Activities activities = new Activities();
        List<Activities> list = activitiesService.selectActivitiesList(activities);
        if (!list.isEmpty()) {
            return success(list.get(0));
        }
        return success(null);
    }
    
    /**
     * 为指定活动生成HTML页面
     */
    @PostMapping("/generate-page/{id}")
    public AjaxResult generatePage(@PathVariable("id") Long id)
    {
        try {
            Activities activity = activitiesService.selectActivitiesById(id);
            if (activity == null) {
                return error("活动不存在");
            }
            
            String fileName = activityPageService.generateActivityPage(activity);
            if (fileName != null) {
                return success("页面生成成功: " + fileName);
            } else {
                return error("页面生成失败");
            }
        } catch (Exception e) {
            return error("生成页面时发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 为所有活动生成HTML页面
     */
    @PostMapping("/generate-all-pages")
    public AjaxResult generateAllPages()
    {
        try {
            Activities activities = new Activities();
            List<Activities> list = activitiesService.selectActivitiesList(activities);
            
            int successCount = 0;
            int failCount = 0;
            
            for (Activities activity : list) {
                try {
                    String fileName = activityPageService.generateActivityPage(activity);
                    if (fileName != null) {
                        successCount++;
                        System.out.println("成功生成页面: " + fileName);
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    failCount++;
                    System.err.println("生成页面失败: " + e.getMessage());
                }
            }
            
            return success(String.format("页面生成完成，成功: %d, 失败: %d", successCount, failCount));
        } catch (Exception e) {
            return error("批量生成页面时发生错误: " + e.getMessage());
        }
    }
} 