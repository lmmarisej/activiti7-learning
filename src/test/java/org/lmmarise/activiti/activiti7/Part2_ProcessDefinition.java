package org.lmmarise.activiti.activiti7;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Part2_ProcessDefinition {
    
    @Autowired
    private RepositoryService repositoryService;
    
    // 查询流程定义
    @Test
    public void getDefinitions() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition pd : list) {
            System.out.println("------流程定义--------");
            System.out.println("Name：" + pd.getName());
            System.out.println("Key：" + pd.getKey());
            System.out.println("ResourceName：" + pd.getResourceName());
            System.out.println("DeploymentId：" + pd.getDeploymentId());
            System.out.println("Version：" + pd.getVersion());
        }
    }
    
    // 删除流程定义
    @Test
    public void delDefinition() {
        String pdID = "730fe28a-3aac-11ed-bdc3-0e5d2288dabb";
        repositoryService.deleteDeployment(pdID, true);     // true 删除流程任务及历史
        System.out.println("删除流程定义成功");
    }
}
