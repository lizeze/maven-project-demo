package com.lzz.project.app;

import com.lzz.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: root-server
 * @BelongsPackage: com.lzz.project.app
 * @Author: lizeze
 * @CreateTime: 2021-04-26 23:19
 * @Description:
 */
@RestController
@RequestMapping("/api/")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("test")
    public String mess() {

        return projectService.sendMessage();
    }
}
