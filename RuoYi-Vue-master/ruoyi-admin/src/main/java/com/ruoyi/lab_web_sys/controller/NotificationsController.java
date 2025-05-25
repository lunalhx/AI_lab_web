package com.ruoyi.lab_web_sys.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lab_web_sys.domain.Notifications;
import com.ruoyi.lab_web_sys.service.INotificationsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通知管理Controller
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@RestController
@RequestMapping("/lab_web_sys/notifications")
public class NotificationsController extends BaseController
{
    @Autowired
    private INotificationsService notificationsService;

    /**
     * 查询通知管理列表
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:notifications:list')")
    @GetMapping("/list")
    public TableDataInfo list(Notifications notifications)
    {
        startPage();
        List<Notifications> list = notificationsService.selectNotificationsList(notifications);
        return getDataTable(list);
    }

    /**
     * 导出通知管理列表
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:notifications:export')")
    @Log(title = "通知管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Notifications notifications)
    {
        List<Notifications> list = notificationsService.selectNotificationsList(notifications);
        ExcelUtil<Notifications> util = new ExcelUtil<Notifications>(Notifications.class);
        util.exportExcel(response, list, "通知管理数据");
    }

    /**
     * 获取通知管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:notifications:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(notificationsService.selectNotificationsById(id));
    }

    /**
     * 新增通知管理
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:notifications:add')")
    @Log(title = "通知管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Notifications notifications)
    {
        return toAjax(notificationsService.insertNotifications(notifications));
    }

    /**
     * 修改通知管理
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:notifications:edit')")
    @Log(title = "通知管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Notifications notifications)
    {
        return toAjax(notificationsService.updateNotifications(notifications));
    }

    /**
     * 删除通知管理
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:notifications:remove')")
    @Log(title = "通知管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(notificationsService.deleteNotificationsByIds(ids));
    }
}
