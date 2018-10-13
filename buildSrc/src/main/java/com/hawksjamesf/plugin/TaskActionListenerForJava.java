package com.hawksjamesf.plugin;

import org.gradle.api.Task;
import org.gradle.api.execution.TaskActionListener;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/13/2018  Sat
 */
public class TaskActionListenerForJava implements TaskActionListener {
    @Override
    public void beforeActions(Task task) {
        util.Console.printJ("action:"+task.toString());
    }

    @Override
    public void afterActions(Task task) {

    }
}
