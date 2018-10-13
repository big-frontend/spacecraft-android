package com.hawksjamesf.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/11/2018  Thu
 */
public class TaskForJava extends DefaultTask {
    private String message;
    private String recipient;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * 当使用`命令行`执行该任务时，才会调用该方法。eg. ./gradlew taskForJava
     */
    @TaskAction
    void say() {
        //dolast
        System.out.printf("taskForJava :%s, %s!\n", getMessage(), getRecipient());
    }
}
