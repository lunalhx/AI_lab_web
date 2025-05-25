package com.ruoyi.lab_web_sys.mapper;

import java.util.List;
import com.ruoyi.lab_web_sys.domain.Activities;

/**
 * 活动管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
public interface ActivitiesMapper 
{
    /**
     * 查询活动管理
     * 
     * @param id 活动管理主键
     * @return 活动管理
     */
    public Activities selectActivitiesById(Long id);

    /**
     * 查询活动管理列表
     * 
     * @param activities 活动管理
     * @return 活动管理集合
     */
    public List<Activities> selectActivitiesList(Activities activities);

    /**
     * 新增活动管理
     * 
     * @param activities 活动管理
     * @return 结果
     */
    public int insertActivities(Activities activities);

    /**
     * 修改活动管理
     * 
     * @param activities 活动管理
     * @return 结果
     */
    public int updateActivities(Activities activities);

    /**
     * 删除活动管理
     * 
     * @param id 活动管理主键
     * @return 结果
     */
    public int deleteActivitiesById(Long id);

    /**
     * 批量删除活动管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivitiesByIds(Long[] ids);
}
