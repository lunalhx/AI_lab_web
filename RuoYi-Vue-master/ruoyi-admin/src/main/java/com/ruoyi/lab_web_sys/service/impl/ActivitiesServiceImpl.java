package com.ruoyi.lab_web_sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lab_web_sys.mapper.ActivitiesMapper;
import com.ruoyi.lab_web_sys.domain.Activities;
import com.ruoyi.lab_web_sys.service.IActivitiesService;
import com.ruoyi.lab_web_sys.service.ActivityPageService;

/**
 * 活动管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@Service
public class ActivitiesServiceImpl implements IActivitiesService 
{
    @Autowired
    private ActivitiesMapper activitiesMapper;
    
    @Autowired
    private ActivityPageService activityPageService;

    /**
     * 查询活动管理
     * 
     * @param id 活动管理主键
     * @return 活动管理
     */
    @Override
    public Activities selectActivitiesById(Long id)
    {
        return activitiesMapper.selectActivitiesById(id);
    }

    /**
     * 查询活动管理列表
     * 
     * @param activities 活动管理
     * @return 活动管理
     */
    @Override
    public List<Activities> selectActivitiesList(Activities activities)
    {
        return activitiesMapper.selectActivitiesList(activities);
    }

    /**
     * 新增活动管理
     * 
     * @param activities 活动管理
     * @return 结果
     */
    @Override
    public int insertActivities(Activities activities)
    {
        int result = activitiesMapper.insertActivities(activities);
        
        // 如果插入成功，生成HTML页面
        if (result > 0) {
            try {
                String fileName = activityPageService.generateActivityPage(activities);
                if (fileName != null) {
                    System.out.println("成功生成活动页面: " + fileName);
                } else {
                    System.err.println("生成活动页面失败");
                }
            } catch (Exception e) {
                System.err.println("生成活动页面时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 修改活动管理
     * 
     * @param activities 活动管理
     * @return 结果
     */
    @Override
    public int updateActivities(Activities activities)
    {
        int result = activitiesMapper.updateActivities(activities);
        
        // 如果更新成功，重新生成HTML页面
        if (result > 0) {
            try {
                String fileName = activityPageService.generateActivityPage(activities);
                if (fileName != null) {
                    System.out.println("成功更新活动页面: " + fileName);
                } else {
                    System.err.println("更新活动页面失败");
                }
            } catch (Exception e) {
                System.err.println("更新活动页面时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 批量删除活动管理
     * 
     * @param ids 需要删除的活动管理主键
     * @return 结果
     */
    @Override
    public int deleteActivitiesByIds(Long[] ids)
    {
        return activitiesMapper.deleteActivitiesByIds(ids);
    }

    /**
     * 删除活动管理信息
     * 
     * @param id 活动管理主键
     * @return 结果
     */
    @Override
    public int deleteActivitiesById(Long id)
    {
        return activitiesMapper.deleteActivitiesById(id);
    }
}
