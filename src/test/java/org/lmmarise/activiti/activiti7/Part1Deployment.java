package org.lmmarise.activiti.activiti7;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 02:56
 */
@SpringBootTest
public class Part1Deployment {
    
    @Autowired
    private RepositoryService repositoryService;
    
    // 通过bpmn部署流程
    @Test
    public void initDeploymentBPMN() {
        String taskXmlFileName = "BPMN/QingJiaLiuCheng.bpmn20.xml";
        String taskImgFileName = "BPMN/QingJiaLiuCheng.bpmn20.png";
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(taskXmlFileName)
                .addClasspathResource(taskImgFileName)        // 图片
                .name("请假流程_V2")
                .deploy();
        System.out.println(deployment);
    }
    
    // 查询流程部署
    @Test
    public void getDeployments() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment dep : list) {
            System.out.println("Id：" + dep.getId());
            System.out.println("Name：" + dep.getName());
            System.out.println("DeploymentTime：" + dep.getDeploymentTime());
            System.out.println("Key：" + dep.getKey());
        }
    }
    
}
