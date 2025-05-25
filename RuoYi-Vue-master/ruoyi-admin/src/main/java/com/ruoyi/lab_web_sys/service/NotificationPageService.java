package com.ruoyi.lab_web_sys.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.ruoyi.lab_web_sys.domain.Notifications;

/**
 * 通知页面生成服务
 * 
 * @author ruoyi
 * @date 2025-05-25
 */
@Service
public class NotificationPageService {
    
    // 模板文件路径
    private static final String TEMPLATE_PATH = "/Users/lunalhx/Desktop/web/web_2/notices_html/20210802.html";
    
    // 输出目录
    private static final String OUTPUT_DIR = "/Users/lunalhx/Desktop/web/web_2/notices_html/";
    
    /**
     * 根据通知信息生成HTML页面
     * 
     * @param notification 通知信息
     * @return 生成的文件名
     */
    public String generateNotificationPage(Notifications notification) {
        try {
            // 读取模板文件
            String template = new String(Files.readAllBytes(Paths.get(TEMPLATE_PATH)), "UTF-8");
            
            // 生成文件名（基于日期）
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileName = sdf.format(notification.getNotificationTime()) + "_" + notification.getId() + ".html";
            
            // 格式化日期
            SimpleDateFormat displaySdf = new SimpleDateFormat("yyyy年M月d日");
            String displayDate = displaySdf.format(notification.getNotificationTime());
            
            // 替换模板中的占位符
            String content = template
                .replace("关于推进文化和旅游领域标准化工作的通知", notification.getNotificationTitle())
                .replace("2021年8月2日", displayDate)
                .replace("<title>关于推进文化和旅游领域标准化工作的通知 - 人工智能与算力技术实验室</title>", 
                        "<title>" + notification.getNotificationTitle() + " - 人工智能与算力技术实验室</title>");
            
            // 替换通知内容
            String noticeContent = generateNoticeContent(notification);
            content = replaceNoticeContent(content, noticeContent);
            
            // 处理下载链接
            content = replaceDownloadSection(content, notification);
            
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
     * 生成通知内容HTML
     */
    private String generateNoticeContent(Notifications notification) {
        String content = notification.getNotificationContent();
        if (content == null || content.trim().isEmpty()) {
            return "<p>通知内容详情请参见附件。</p>";
        }
        
        // 如果内容已经是HTML格式，直接返回
        if (content.contains("<p>") || content.contains("<div>")) {
            return content;
        }
        
        // 否则将纯文本转换为段落
        String[] paragraphs = content.split("\n");
        StringBuilder html = new StringBuilder();
        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                html.append("<p>").append(paragraph.trim()).append("</p>\n");
            }
        }
        
        return html.toString();
    }
    
    /**
     * 替换模板中的通知内容部分
     */
    private String replaceNoticeContent(String template, String newContent) {
        // 找到通知内容的开始和结束位置
        String startMarker = "<div class=\"notice-text\">";
        String endMarker = "</div>";
        
        int startIndex = template.indexOf(startMarker);
        if (startIndex == -1) return template;
        
        int contentStart = startIndex + startMarker.length();
        int endIndex = template.indexOf(endMarker, contentStart);
        if (endIndex == -1) return template;
        
        // 替换内容
        StringBuilder result = new StringBuilder();
        result.append(template.substring(0, contentStart));
        result.append("\n                        ");
        result.append(newContent);
        result.append("\n                    ");
        result.append(template.substring(endIndex));
        
        return result.toString();
    }
    
    /**
     * 替换下载部分
     */
    private String replaceDownloadSection(String template, Notifications notification) {
        String downloadSection;
        
        if (notification.getNotificationFileUrl() != null && 
            !notification.getNotificationFileUrl().trim().isEmpty() &&
            !notification.getNotificationFileUrl().equals("null")) {
            
            // 有附件的情况
            String fileUrl = notification.getNotificationFileUrl();
            String fileName = notification.getNotificationTitle();
            
            downloadSection = String.format(
                "                    <div class=\"notice-download\">\n" +
                "                        <p class=\"file-note\">点击下方按钮下载完整通知文件：</p>\n" +
                "                        <div class=\"download-options\">\n" +
                "                            <a href=\"%s\" class=\"download-btn\" target=\"_blank\">\n" +
                "                                <i class=\"fas fa-file-pdf\"></i> 在浏览器中查看PDF\n" +
                "                            </a>\n" +
                "                            <a href=\"%s\" class=\"download-btn\" download>\n" +
                "                                <i class=\"fas fa-download\"></i> 下载PDF文件\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                    </div>", fileUrl, fileUrl);
        } else {
            // 无附件的情况
            downloadSection = "                    <!-- 无附件 -->";
        }
        
        // 找到下载部分并替换
        String startMarker = "<div class=\"notice-download\">";
        String endMarker = "</div>\n                </div>";
        
        int startIndex = template.indexOf(startMarker);
        if (startIndex == -1) return template;
        
        int endIndex = template.indexOf(endMarker, startIndex);
        if (endIndex == -1) return template;
        
        endIndex += endMarker.length() - "</div>".length(); // 保留最后的 </div>
        
        StringBuilder result = new StringBuilder();
        result.append(template.substring(0, startIndex));
        result.append(downloadSection);
        result.append("\n                ");
        result.append(template.substring(endIndex));
        
        return result.toString();
    }
} 