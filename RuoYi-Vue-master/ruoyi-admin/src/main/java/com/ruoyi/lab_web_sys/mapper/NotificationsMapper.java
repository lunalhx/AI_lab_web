package com.ruoyi.lab_web_sys.mapper;

import java.util.List;
import com.ruoyi.lab_web_sys.domain.Notifications;

/**
 * 通知管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
public interface NotificationsMapper 
{
    /**
     * 查询通知管理
     * 
     * @param id 通知管理主键
     * @return 通知管理
     */
    public Notifications selectNotificationsById(Long id);

    /**
     * 查询通知管理列表
     * 
     * @param notifications 通知管理
     * @return 通知管理集合
     */
    public List<Notifications> selectNotificationsList(Notifications notifications);

    /**
     * 新增通知管理
     * 
     * @param notifications 通知管理
     * @return 结果
     */
    public int insertNotifications(Notifications notifications);

    /**
     * 修改通知管理
     * 
     * @param notifications 通知管理
     * @return 结果
     */
    public int updateNotifications(Notifications notifications);

    /**
     * 删除通知管理
     * 
     * @param id 通知管理主键
     * @return 结果
     */
    public int deleteNotificationsById(Long id);

    /**
     * 批量删除通知管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNotificationsByIds(Long[] ids);
}
