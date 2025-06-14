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
import com.ruoyi.lab_web_sys.domain.Activities;
import com.ruoyi.lab_web_sys.service.IActivitiesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动管理Controller
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@RestController
@RequestMapping("/lab_web_sys/activities")
public class ActivitiesController extends BaseController
{
    @Autowired
    private IActivitiesService activitiesService;

    /**
     * 查询活动管理列表
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:activities:list')")
    @GetMapping("/list")
    public TableDataInfo list(Activities activities)
    {
        startPage();
        List<Activities> list = activitiesService.selectActivitiesList(activities);
        return getDataTable(list);
    }

    /**
     * 导出活动管理列表
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:activities:export')")
    @Log(title = "活动管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Activities activities)
    {
        List<Activities> list = activitiesService.selectActivitiesList(activities);
        ExcelUtil<Activities> util = new ExcelUtil<Activities>(Activities.class);
        util.exportExcel(response, list, "活动管理数据");
    }

    /**
     * 获取活动管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:activities:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(activitiesService.selectActivitiesById(id));
    }

    /**
     * 新增活动管理
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:activities:add')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Activities activities)
    {
        return toAjax(activitiesService.insertActivities(activities));
    }

    /**
     * 修改活动管理
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:activities:edit')")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Activities activities)
    {
        return toAjax(activitiesService.updateActivities(activities));
    }

    /**
     * 删除活动管理
     */
    @PreAuthorize("@ss.hasPermi('lab_web_sys:activities:remove')")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activitiesService.deleteActivitiesByIds(ids));
    }
}
