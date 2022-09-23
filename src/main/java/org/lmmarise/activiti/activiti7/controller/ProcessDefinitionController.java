package org.lmmarise.activiti.activiti7.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 流程定义相关。
 *
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 15:26
 */
@RestController
@RequestMapping("/process-definitions")
public class ProcessDefinitionController {
    
    @Autowired
    private RepositoryService repositoryService;
    
    /**
     * 分页查询流程定义，示例：page?page=1&size=15&sort=field1,DESC&sort=field2,ASC
     */
    @GetMapping("/page")
    public List<Map<String, Object>> getDefinitions(@PageableDefault(page = 1, size = 15) Pageable pageable) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .listPage(pageable.getPageNumber(), pageable.getPageSize());
        for (ProcessDefinition pd : processDefinitions) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("Name", pd.getName());
            item.put("Key", pd.getKey());
            item.put("ResourceName", pd.getResourceName());
            item.put("DeploymentId", pd.getDeploymentId());
            item.put("Version", pd.getVersion());
            result.add(item);
        }
        return result;
    }
    
    /**
     * 通过 bpmn 文件或 zip 文件进行部署流程。
     */
    @PostMapping("/file-upload-deploy")
    public String uploadStreamAndDeploymentByFile(@RequestParam("processFile") MultipartFile multipartFile,
                                                  @RequestParam("deploymentName") String deploymentName) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        String extName = FilenameUtils.getExtension(filename);
        InputStream fileInputStream = multipartFile.getInputStream();
        
        Deployment deployment;
        if ("zip".equals(extName)) {
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            deployment = repositoryService.createDeployment()
                    .addZipInputStream(zipInputStream)
                    .name(deploymentName)
                    .deploy();
        } else {
            deployment = repositoryService.createDeployment()
                    .addInputStream(filename, fileInputStream)
                    .name(deploymentName)
                    .deploy();
        }
        return deployment.getId() + ";" + filename;
    }
    
    /**
     * 在线直接基于 String 部署流程。
     */
    @PostMapping("/string-upload-deploy")
    public String uploadStreamAndDeploymentByString(@RequestParam("stringBpmn") String stringBpmn,
                                                    @RequestParam("deploymentName") String deploymentName) {
        Deployment deployment = repositoryService.createDeployment()
                .addString("CreateWithBpmnJs.bpmn", stringBpmn)
                .name(deploymentName)
                .deploy();
        return deployment.getId();
    }
    
}
