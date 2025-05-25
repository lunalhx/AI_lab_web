package com.ruoyi.lab_web_sys.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.ruoyi.lab_web_sys.domain.Activities;

/**
 * 活动页面生成服务
 * 
 * @author ruoyi
 * @date 2025-05-25
 */
@Service
public class ActivityPageService {
    
    // 模板文件路径
    private static final String TEMPLATE_PATH = "/Users/lunalhx/Desktop/web/web_2/activities_html/2024-10-18.html";
    
    // 输出目录
    private static final String OUTPUT_DIR = "/Users/lunalhx/Desktop/web/web_2/activities_html/";
    
    /**
     * 根据活动信息生成HTML页面
     * 
     * @param activity 活动信息
     * @return 生成的文件名
     */
    public String generateActivityPage(Activities activity) {
        try {
            // 读取模板文件
            String template = new String(Files.readAllBytes(Paths.get(TEMPLATE_PATH)), "UTF-8");
            
            // 生成文件名（基于日期）
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fileName = sdf.format(activity.getActivityTime()) + ".html";
            
            // 格式化日期
            SimpleDateFormat displaySdf = new SimpleDateFormat("yyyy年M月d日");
            String displayDate = displaySdf.format(activity.getActivityTime());
            
            // 替换模板中的占位符
            String content = template
                .replace("人工智能与算力技术重点实验室交流座谈", activity.getActivityTitle())
                .replace("2024年10月18日", displayDate)
                .replace("<title>人工智能与算力技术重点实验室交流座谈 - 人工智能与算力技术实验室</title>", 
                        "<title>" + activity.getActivityTitle() + " - 人工智能与算力技术实验室</title>");
            
            // 替换活动元信息
            content = replaceActivityMeta(content, activity);
            
            // 替换活动内容
            String activityContent = generateActivityContent(activity);
            content = replaceActivityContent(content, activityContent);
            
            // 处理活动图片
            content = replaceActivityPhotos(content, activity);
            
            // 写入文件
            File outputFile = new File(OUTPUT_DIR + fileName);
            try (FileWriter writer = new FileWriter(outputFile, false)) {
                writer.write(content);
            }
            
            return fileName;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 替换活动元信息
     */
    private String replaceActivityMeta(String template, Activities activity) {
        // 替换地点
        if (activity.getActivityLocation() != null) {
            template = template.replace("飞云楼209", activity.getActivityLocation());
        }
        
        // 替换主持人
        if (activity.getActivityHost() != null) {
            template = template.replace("<span>主持人：</span>", 
                "<span>主持人：" + activity.getActivityHost() + "</span>");
        }
        
        // 替换记录人
        if (activity.getActivityRecorder() != null) {
            template = template.replace("记录人：郭玉梅", 
                "记录人：" + activity.getActivityRecorder());
        }
        
        return template;
    }
    
    /**
     * 生成活动内容HTML
     */
    private String generateActivityContent(Activities activity) {
        StringBuilder content = new StringBuilder();
        
        // 参会人员
        if (activity.getActivityAttendees() != null && !activity.getActivityAttendees().trim().isEmpty()) {
            content.append("<div class=\"activity-participants\">\n");
            content.append("                        <h2>参会人员</h2>\n");
            content.append("                        <p>").append(activity.getActivityAttendees()).append("</p>\n");
            content.append("                    </div>\n");
        }
        
        // 活动内容
        if (activity.getActivityContent() != null && !activity.getActivityContent().trim().isEmpty()) {
            content.append("                    <div class=\"activity-summary\">\n");
            content.append("                        <h2>活动内容</h2>\n");
            
            String activityContent = activity.getActivityContent();
            if (activityContent.contains("<p>") || activityContent.contains("<div>")) {
                // 如果已经是HTML格式，直接使用
                content.append("                        ").append(activityContent).append("\n");
            } else {
                // 否则将纯文本转换为段落
                String[] paragraphs = activityContent.split("\n");
                for (String paragraph : paragraphs) {
                    if (!paragraph.trim().isEmpty()) {
                        content.append("                        <p>").append(paragraph.trim()).append("</p>\n");
                    }
                }
            }
            content.append("                    </div>\n");
        }
        
        return content.toString();
    }
    
    /**
     * 替换模板中的活动内容部分
     */
    private String replaceActivityContent(String template, String newContent) {
        // 找到活动内容的开始和结束位置
        String startMarker = "<div class=\"activity-participants\">";
        String endMarker = "</div>\n                </div>\n                \n                <div class=\"activity-photos\">";
        
        int startIndex = template.indexOf(startMarker);
        if (startIndex == -1) return template;
        
        int endIndex = template.indexOf(endMarker, startIndex);
        if (endIndex == -1) return template;
        
        // 替换内容
        StringBuilder result = new StringBuilder();
        result.append(template.substring(0, startIndex));
        result.append(newContent);
        result.append("                ");
        result.append(template.substring(endIndex));
        
        return result.toString();
    }
    
    /**
     * 替换活动图片部分
     */
    private String replaceActivityPhotos(String template, Activities activity) {
        StringBuilder photoSection = new StringBuilder();
        photoSection.append("<div class=\"activity-photos\">\n");
        photoSection.append("                        <h2>活动照片</h2>\n");
        
        boolean hasPhotos = false;
        
        // 检查是否有图片
        if (activity.getActivityImageUrl1() != null && !activity.getActivityImageUrl1().trim().isEmpty()) {
            hasPhotos = true;
        }
        if (activity.getActivityImageUrl2() != null && !activity.getActivityImageUrl2().trim().isEmpty()) {
            hasPhotos = true;
        }
        if (activity.getActivityImageUrl3() != null && !activity.getActivityImageUrl3().trim().isEmpty()) {
            hasPhotos = true;
        }
        
        if (hasPhotos) {
            photoSection.append("                        <div class=\"photo-gallery\">\n");
            
            if (activity.getActivityImageUrl1() != null && !activity.getActivityImageUrl1().trim().isEmpty()) {
                photoSection.append("                            <div class=\"photo-item\">\n");
                photoSection.append("                                <img src=\"").append(activity.getActivityImageUrl1()).append("\" alt=\"").append(activity.getActivityTitle()).append("照片1\" onerror=\"this.onerror=null; this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAADICAYAAAADLmgdAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NUJCMkY1OTlEQTI4MTFFQkFEQTg4NkFGMzlGNzIyMTQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NUJCMkY1OUFEQTI4MTFFQkFEQTg4NkFGMzlGNzIyMTQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo1QkIyRjU5N0RBMjgxMUVCQURBODg2QUYzOUY3MjIxNCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo1QkIyRjU5OERBMjgxMUVCQURBODg2QUYzOUY3MjIxNCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pn90C64AAAQ7SURBVHja7NyxjdtAFEXRT4YEByvQClSGgFaglFSB0lEZClqGOlAXrA4UTOxNBgvLHM68Q54DvJRA6OK+QYAflqF5PJ/P9eJ8kFr7SqnXR/CXz6azvjcL5TYawMuXcIw+D9YeD4dn3Mj37+9WMPv5+bktwIWt64JMAIBMAIBMAIBMAIBMAIBMAIBMAIBMAIBMAPwCk7XGF0IBoOICxPgGCMgEAMgEAMgEAMgEAMgEsLEA+IcG5hn5AKDiAgyfAYJMAIBMAIBMAIBMAIBMAIBMAIBIBMALzEZK3jhXJrFxARADB7AYb/oQJwYeu6IBMAIBMAIBMAIBMAIBMAIBMAIBMAIBMA/y1r50+tAaBiAQDgYgFgfAMEmQAAmQAAmQAAmQAAmQD43wLgBwDmGfkAoOICXPA7GCABUMZPggEJgDK+EQZIAJTxLzEgAVDGN8OABEAZPwkGJADK+GYYkAAo46fBgARAGT8NBiQAyvhpMGAlGmDqP06dUUACoIxPhAEJgDI+EQYkAMr4ZhiQACjj02BAAqCMnwwDEgBlfCIMMOP6mXr/jQJgaQHS9x8JwNACGKCAGVbmGfmAJQXIPkAZbwYogzIAJvmRt/FmsNILEHqAGVbmGfmAFQogAE4WQAgdL5Q7qwD5jZ+oACEPI/hhiAYQACcKkG38RAUwQAEzrMwz8gEgEwAgEwAgEwAgEwAgEwAgE0De+KkKYIASADMZVDZ+qgLEPAx+GcYAJQAGMagYnDqgALkaP0kBzADFAKVgZZ6RDzCeQQU/+C4qANcFXvTQHwAwk0HF+KkK4NdhhMD0BpWNn6YA3hAgEGYzKPhBd1UBuC7wogcA0xlQNLg/QTLDyjxjXWEAzGhQ0dA+A4QB8JJB2fhJCuAzQNPvv8/B6gJgEoPKxk9RgJSOF8AoBlXjpygA8aM+A4QB8JJBZeMnKEBKxwtgBIOq8RMUgPhRmwHCAHjRoKrxFy9ASscLYhSDbPyFCpDS8RIfhVEMsvEzFAAGRy0GCAMgzCA5G3+RApAeRXwUTGAQ3AVYXAAS498QIAiAbIPgj34XL0BKxwsiDAqxwC7/4gVI6Xjhjzps8OXDLVuAlI4X/qhIg+j0B8AAIQQCDLLxj48AEuPfEEDIYCSDqsaDuiCZYWWesSa8AQIGkQ2+fLiFFyClJxR+1JGDo9MfAANECARkkI2fHgFQgMtY4H5LxwsYvBsLPO7YAiTGvyEA7YcBcH0ssNvSEwtYuAQCgwlW5hl4AwQYvO0zgLCzAJfHAvdbeiLhyxkgLLB/LABccGIBGrvYWOD+GfkPVH4a4gZ3s7HAF08t/CFHJIDJPrEI4EcL7LDnkh+s9EVhI90WYDWxSQRg6DclgPFugIBDPgOEHXfHzAA3jrcFYIQbASDGux3AlY8BYg+4gBH20S6DJd4AAAAAAAAAAAAAAADwzC8BBgBu3En5yjHlnAAAAABJRU5ErkJggg=='; this.alt='图片加载失败';\">\n");
                photoSection.append("                            </div>\n");
            }
            
            if (activity.getActivityImageUrl2() != null && !activity.getActivityImageUrl2().trim().isEmpty()) {
                photoSection.append("                            <div class=\"photo-item\">\n");
                photoSection.append("                                <img src=\"").append(activity.getActivityImageUrl2()).append("\" alt=\"").append(activity.getActivityTitle()).append("照片2\" onerror=\"this.onerror=null; this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAADICAYAAAADLmgdAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NUJCMkY1OTlEQTI4MTFFQkFEQTg4NkFGMzlGNzIyMTQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NUJCMkY1OUFEQTI4MTFFQkFEQTg4NkFGMzlGNzIyMTQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo1QkIyRjU5N0RBMjgxMUVCQURBODg2QUYzOUY3MjIxNCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo1QkIyRjU5OERBMjgxMUVCQURBODg2QUYzOUY3MjIxNCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pn90C64AAAQ7SURBVHja7NyxjdtAFEXRT4YEByvQClSGgFaglFSB0lEZClqGOlAXrA4UTOxNBgvLHM68Q54DvJRA6OK+QYAflqF5PJ/P9eJ8kFr7SqnXR/CXz6azvjcL5TYawMuXcIw+D9YeD4dn3Mj37+9WMPv5+bktwIWt64JMAIBMAIBMAIBMAIBMAIBMAIBMAIBMAIBMAPwCk7XGF0IBoOICxPgGCMgEAMgEAMgEAMgEAMgEsLEA+IcG5hn5AKDiAgyfAYJMAIBMAIBMAIBMAIBMAIBMAIBIBMALzEZK3jhXJrFxARADB7AYb/oQJwYeu6IBMAIBMAIBMAIBMAIBMAIBMAIBMAIBMA/y1r50+tAaBiAQDgYgFgfAMEmQAAmQAAmQAAmQAAmQD43wLgBwDmGfkAoOICXPA7GCABUMZPggEJgDK+EQZIAJTxLzEgAVDGN8OABEAZPwkGJADK+GYYkAAo46fBgARAGT8NBiQAyvhpMGAlGmDqP06dUUACoIxPhAEJgDI+EQYkAMr4ZhiQACjj02BAAqCMnwwDEgBlfCIMMOP6mXr/jQJgaQHS9x8JwNACGKCAGVbmGfmAJQXIPkAZbwYogzIAJvmRt/FmsNILEHqAGVbmGfmAFQogAE4WQAgdL5Q7qwD5jZ+oACEPI/hhiAYQACcKkG38RAUwQAEzrMwz8gEgEwAgEwAgEwAgEwAgEwAgE0De+KkKYIASADMZVDZ+qgLEPAx+GcYAJQAGMagYnDqgALkaP0kBzADFAKVgZZ6RDzCeQQU/+C4qANcFXvTQHwAwk0HF+KkK4NdhhMD0BpWNn6YA3hAgEGYzKPhBd1UBuC7wogcA0xlQNLg/QTLDyjxjXWEAzGhQ0dA+A4QB8JJB2fhJCuAzQNPvv8/B6gJgEoPKxk9RgJSOF8AoBlXjpygA8aM+A4QB8JJBZeMnKEBKxwtgBIOq8RMUgPhRmwHCAHjRoKrxFy9ASscLYhSDbPyFCpDS8RIfhVEMsvEzFAAGRy0GCAMgzCA5G3+RApAeRXwUTGAQ3AVYXAAS498QIAiAbIPgj34XL0BKxwsiDAqxwC7/4gVI6Xjhjzps8OXDLVuAlI4X/qhIg+j0B8AAIQQCDLLxj48AEuPfEEDIYCSDqsaDuiCZYWWesSa8AQIGkQ2+fLiFFyClJxR+1JGDo9MfAANECARkkI2fHgFQgMtY4H5LxwsYvBsLPO7YAiTGvyEA7YcBcH0ssNvSEwtYuAQCgwlW5hl4AwQYvO0zgLCzAJfHAvdbeiLhyxkgLLB/LABccGIBGrvYWOD+GfkPVH4a4gZ3s7HAF08t/CFHJIDJPrEI4EcL7LDnkh+s9EVhI90WYDWxSQRg6DclgPFugIBDPgOEHXfHzAA3jrcFYIQbASDGux3AlY8BYg+4gBH20S6DJd4AAAAAAAAAAAAAAADwzC8BBgBu3En5yjHlnAAAAABJRU5ErkJggg=='; this.alt='图片加载失败';\">\n");
                photoSection.append("                            </div>\n");
            }
            
            if (activity.getActivityImageUrl3() != null && !activity.getActivityImageUrl3().trim().isEmpty()) {
                photoSection.append("                            <div class=\"photo-item\">\n");
                photoSection.append("                                <img src=\"").append(activity.getActivityImageUrl3()).append("\" alt=\"").append(activity.getActivityTitle()).append("照片3\" onerror=\"this.onerror=null; this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAADICAYAAAADLmgdAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NUJCMkY1OTlEQTI4MTFFQkFEQTg4NkFGMzlGNzIyMTQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NUJCMkY1OUFEQTI4MTFFQkFEQTg4NkFGMzlGNzIyMTQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo1QkIyRjU5N0RBMjgxMUVCQURBODg2QUYzOUY3MjIxNCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo1QkIyRjU5OERBMjgxMUVCQURBODg2QUYzOUY3MjIxNCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pn90C64AAAQ7SURBVHja7NyxjdtAFEXRT4YEByvQClSGgFaglFSB0lEZClqGOlAXrA4UTOxNBgvLHM68Q54DvJRA6OK+QYAflqF5PJ/P9eJ8kFr7SqnXR/CXz6azvjcL5TYawMuXcIw+D9YeD4dn3Mj37+9WMPv5+bktwIWt64JMAIBMAIBMAIBMAIBMAIBMAIBMAIBMAIBMAPwCk7XGF0IBoOICxPgGCMgEAMgEAMgEAMgEAMgEsLEA+IcG5hn5AKDiAgyfAYJMAIBMAIBMAIBMAIBMAIBMAIBIBMALzEZK3jhXJrFxARADB7AYb/oQJwYeu6IBMAIBMAIBMAIBMAIBMAIBMAIBMAIBMA/y1r50+tAaBiAQDgYgFgfAMEmQAAmQAAmQAAmQAAmQD43wLgBwDmGfkAoOICXPA7GCABUMZPggEJgDK+EQZIAJTxLzEgAVDGN8OABEAZPwkGJADK+GYYkAAo46fBgARAGT8NBiQAyvhpMGAlGmDqP06dUUACoIxPhAEJgDI+EQYkAMr4ZhiQACjj02BAAqCMnwwDEgBlfCIMMOP6mXr/jQJgaQHS9x8JwNACGKCAGVbmGfmAJQXIPkAZbwYogzIAJvmRt/FmsNILEHqAGVbmGfmAFQogAE4WQAgdL5Q7qwD5jZ+oACEPI/hhiAYQACcKkG38RAUwQAEzrMwz8gEgEwAgEwAgEwAgEwAgEwAgE0De+KkKYIASADMZVDZ+qgLEPAx+GcYAJQAGMagYnDqgALkaP0kBzADFAKVgZZ6RDzCeQQU/+C4qANcFXvTQHwAwk0HF+KkK4NdhhMD0BpWNn6YA3hAgEGYzKPhBd1UBuC7wogcA0xlQNLg/QTLDyjxjXWEAzGhQ0dA+A4QB8JJB2fhJCuAzQNPvv8/B6gJgEoPKxk9RgJSOF8AoBlXjpygA8aM+A4QB8JJBZeMnKEBKxwtgBIOq8RMUgPhRmwHCAHjRoKrxFy9ASscLYhSDbPyFCpDS8RIfhVEMsvEzFAAGRy0GCAMgzCA5G3+RApAeRXwUTGAQ3AVYXAAS498QIAiAbIPgj34XL0BKxwsiDAqxwC7/4gVI6Xjhjzps8OXDLVuAlI4X/qhIg+j0B8AAIQQCDLLxj48AEuPfEEDIYCSDqsaDuiCZYWWesSa8AQIGkQ2+fLiFFyClJxR+1JGDo9MfAANECARkkI2fHgFQgMtY4H5LxwsYvBsLPO7YAiTGvyEA7YcBcH0ssNvSEwtYuAQCgwlW5hl4AwQYvO0zgLCzAJfHAvdbeiLhyxkgLLB/LABccGIBGrvYWOD+GfkPVH4a4gZ3s7HAF08t/CFHJIDJPrEI4EcL7LDnkh+s9EVhI90WYDWxSQRg6DclgPFugIBDPgOEHXfHzAA3jrcFYIQbASDGux3AlY8BYg+4gBH20S6DJd4AAAAAAAAAAAAAAADwzC8BBgBu3En5yjHlnAAAAABJRU5ErkJggg=='; this.alt='图片加载失败';\">\n");
                photoSection.append("                            </div>\n");
            }
            
            photoSection.append("                        </div>\n");
            photoSection.append("                        <p class=\"photo-caption\">").append(activity.getActivityTitle()).append("现场</p>\n");
        } else {
            photoSection.append("                        <p>暂无活动照片</p>\n");
        }
        
        photoSection.append("                    </div>");
        
        // 找到照片部分并替换
        String startMarker = "<div class=\"activity-photos\">";
        String endMarker = "</div>\n            </div>\n        </div>\n    </section>";
        
        int startIndex = template.indexOf(startMarker);
        if (startIndex == -1) return template;
        
        int endIndex = template.indexOf(endMarker, startIndex);
        if (endIndex == -1) return template;
        
        StringBuilder result = new StringBuilder();
        result.append(template.substring(0, startIndex));
        result.append(photoSection.toString());
        result.append("\n            ");
        result.append(template.substring(endIndex));
        
        return result.toString();
    }
} 