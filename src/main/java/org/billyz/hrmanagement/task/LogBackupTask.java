package org.billyz.hrmanagement.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;

@Component
public class LogBackupTask {
    
    private static final Logger logger = LoggerFactory.getLogger(LogBackupTask.class);
    
    /**
     * 每天凌晨0点执行日志备份任务
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void backupLogs() {
        logger.info("开始执行日志备份任务");
        
        try {
            String logsDir = "logs";
            String backupDir = "logs/bak";
            
            // 创建备份目录
            Path backupPath = Paths.get(backupDir);
            if (!Files.exists(backupPath)) {
                Files.createDirectories(backupPath);
            }
            
            // 获取前一日的日期格式，用于查找需要备份的日志文件
            String yesterdayStr = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            // 查找前一日的小时级日志文件并进行压缩备份
            File logsDirectory = new File(logsDir);
            // 查找前一日的小时级日志文件，格式为 hr-app.yyyy-MM-dd-HH.log
            File[] yesterdayLogFiles = logsDirectory.listFiles((dir, name) -> 
                name.startsWith("hr-app." + yesterdayStr + "-") && name.endsWith(".log"));
            
            if (yesterdayLogFiles != null && yesterdayLogFiles.length > 0) {
                logger.info("找到 {} 个前一日日志文件需要备份", yesterdayLogFiles.length);
                
                // 创建备份目录
                String backupFileName = "hr-app-" + yesterdayStr + ".log.gz";
                Path backupFilePath = Paths.get(backupDir, backupFileName);
                
                // 合并所有前一日的日志文件内容
                StringBuilder combinedContent = new StringBuilder();
                for (File logFile : yesterdayLogFiles) {
                    logger.info("正在处理日志文件: {}", logFile.getName());
                    String content = Files.readString(logFile.toPath());
                    combinedContent.append(content).append("\n");
                    
                    // 备份完成后删除原文件
                    Files.delete(logFile.toPath());
                }
                
                // 将合并后的内容写入压缩文件
                if (combinedContent.length() > 0) { // 只有在有内容时才创建压缩文件
                    try (java.util.zip.GZIPOutputStream gos = new java.util.zip.GZIPOutputStream(
                            Files.newOutputStream(backupFilePath))) {
                        gos.write(combinedContent.toString().getBytes());
                    }
                } else {
                    logger.info("没有内容需要备份到: {}", backupFilePath.toString());
                }
                
                logger.info("日志文件已成功备份到: {}", backupFilePath.toString());
            } else {
                logger.info("没有找到需要备份的前一日日志文件");
            }
            
            logger.info("日志备份任务执行完成");
            
        } catch (Exception e) {
            logger.error("日志备份任务执行失败", e);
        }
    }
}
