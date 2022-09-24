package org.lmmarise.activiti.activiti7.controller;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 22:13
 */
@RestController
@RequestMapping("/process/instances")
public class ProcessInstanceController {
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private ProcessRuntime processRuntime;
    
    @Autowired
    private RuntimeService runtimeService;
    
    /**
     * 查询流程实例。
     */
    @GetMapping("/page")
    public List<Map<String, Object>> getInstances(@PageableDefault(page = 1, size = 15) Pageable pageable) {
        List<ProcessInstance> processInstanceList = processRuntime
                .processInstances(org.activiti.api.runtime.shared.query.Pageable.of(pageable.getPageSize(), pageable.getPageSize()))
                .getContent();
        processInstanceList.sort(Comparator.comparing(x -> x.getStartDate().toString()));
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (ProcessInstance pi : processInstanceList) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", pi.getId());
            item.put("name", pi.getName());
            item.put("status", pi.getStatus());
            item.put("processDefinitionId", pi.getProcessDefinitionId());
            item.put("processDefinitionKey", pi.getProcessDefinitionKey());
            item.put("startDate", pi.getStartDate());
            item.put("processDefinitionVersion", pi.getProcessDefinitionVersion());
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(pi.getProcessDefinitionId())
                    .singleResult();
            item.put("deploymentId", pd.getDeploymentId());
            item.put("resourceName", pd.getResourceName());
            result.add(item);
        }
        return result;
    }
    
    /**
     * 启动流程实例。
     */
    @GetMapping(value = "/start")
    public String startProcess(@RequestParam("processDefinitionKey") String processDefinitionKey,
                               @RequestParam("instanceName") String instanceName,
                               @RequestParam("instanceVariable") String instanceVariable) {
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start()
                .withProcessDefinitionKey(processDefinitionKey)
                .withName(instanceName)
                .withVariable("content", instanceVariable)
                .withVariable("参数2", "参数2的值")
                .withBusinessKey("自定义BusinessKey")
                .build());
        return processInstance.getName() + ";" + processInstance.getId();
    }
    
}
