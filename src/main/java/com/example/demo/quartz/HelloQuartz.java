package com.example.demo.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * @author yaozhengming
 * @date 2019.3.6
 */
public class HelloQuartz implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        JobDetail detail = context.getJobDetail();
        String name = detail.getJobDataMap().getString("name");
        System.out.println("\n\n ---------------------------------------say hello to " + name + " at " + new Date() + "\n\n");
    }
}