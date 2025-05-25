package com.ruoyi.lab_web_sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lab_web_sys.mapper.NotificationsMapper;
import com.ruoyi.lab_web_sys.domain.Notifications;
import com.ruoyi.lab_web_sys.service.INotificationsService;
import com.ruoyi.lab_web_sys.service.NotificationPageService;

/**
 * 通知管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@Service
public class NotificationsServiceImpl implements INotificationsService 
{
    @Autowired
    private NotificationsMapper notificationsMapper;
    
    @Autowired
    private NotificationPageService notificationPageService;

    /**
     * 查询通知管理
     * 
     * @param id 通知管理主键
     * @return 通知管理
     */
    @Override
    public Notifications selectNotificationsById(Long id)
    {
        return notificationsMapper.selectNotificationsById(id);
    }

    /**
     * 查询通知管理列表
     * 
     * @param notifications 通知管理
     * @return 通知管理
     */
    @Override
    public List<Notifications> selectNotificationsList(Notifications notifications)
    {
        return notificationsMapper.selectNotificationsList(notifications);
    }

    /**
     * 新增通知管理
     * 
     * @param notifications 通知管理
     * @return 结果
     */
    @Override
    public int insertNotifications(Notifications notifications)
    {
        int result = notificationsMapper.insertNotifications(notifications);
        
        // 如果插入成功，生成HTML页面
        if (result > 0) {
            try {
                String fileName = notificationPageService.generateNotificationPage(notifications);
                if (fileName != null) {
                    System.out.println("成功生成通知页面: " + fileName);
                } else {
                    System.err.println("生成通知页面失败");
                }
            } catch (Exception e) {
                System.err.println("生成通知页面时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 修改通知管理
     * 
     * @param notifications 通知管理
     * @return 结果
     */
    @Override
    public int updateNotifications(Notifications notifications)
    {
        int result = notificationsMapper.updateNotifications(notifications);
        
        // 如果更新成功，重新生成HTML页面
        if (result > 0) {
            try {
                String fileName = notificationPageService.generateNotificationPage(notifications);
                if (fileName != null) {
                    System.out.println("成功更新通知页面: " + fileName);
                } else {
                    System.err.println("更新通知页面失败");
                }
            } catch (Exception e) {
                System.err.println("更新通知页面时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 批量删除通知管理
     * 
     * @param ids 需要删除的通知管理主键
     * @return 结果
     */
    @Override
    public int deleteNotificationsByIds(Long[] ids)
    {
        return notificationsMapper.deleteNotificationsByIds(ids);
    }

    /**
     * 删除通知管理信息
     * 
     * @param id 通知管理主键
     * @return 结果
     */
    @Override
    public int deleteNotificationsById(Long id)
    {
        return notificationsMapper.deleteNotificationsById(id);
    }
}
