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
import com.ruoyi.lab_web_sys.domain.Notifications;
import com.ruoyi.lab_web_sys.service.INotificationsService;
import com.ruoyi.lab_web_sys.service.NotificationPageService;

/**
 * 公开通知公告API控制器
 * 提供无需认证的通知公告接口供外部网站调用
 * 
 * @author ruoyi
 * @date 2025-05-25
 */
@RestController
@RequestMapping("/api/public/notifications")
@CrossOrigin(origins = "*")
public class PublicNotificationsController extends BaseController
{
    @Autowired
    private INotificationsService notificationsService;
    
    @Autowired
    private NotificationPageService notificationPageService;

    /**
     * 获取通知公告列表
     */
    @GetMapping("/list")
    public AjaxResult list()
    {
        Notifications notifications = new Notifications();
        List<Notifications> list = notificationsService.selectNotificationsList(notifications);
        return success(list);
    }

    /**
     * 根据ID获取通知公告详细信息
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(notificationsService.selectNotificationsById(id));
    }

    /**
     * 获取最新的通知公告
     */
    @GetMapping("/latest")
    public AjaxResult getLatest()
    {
        Notifications notifications = new Notifications();
        List<Notifications> list = notificationsService.selectNotificationsList(notifications);
        if (!list.isEmpty()) {
            return success(list.get(0));
        }
        return success(null);
    }
    
    /**
     * 为指定通知生成HTML页面
     */
    @PostMapping("/generate-page/{id}")
    public AjaxResult generatePage(@PathVariable("id") Long id)
    {
        try {
            Notifications notification = notificationsService.selectNotificationsById(id);
            if (notification == null) {
                return error("通知不存在");
            }
            
            String fileName = notificationPageService.generateNotificationPage(notification);
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
     * 为所有通知生成HTML页面
     */
    @PostMapping("/generate-all-pages")
    public AjaxResult generateAllPages()
    {
        try {
            Notifications notifications = new Notifications();
            List<Notifications> list = notificationsService.selectNotificationsList(notifications);
            
            int successCount = 0;
            int failCount = 0;
            
            for (Notifications notification : list) {
                try {
                    String fileName = notificationPageService.generateNotificationPage(notification);
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