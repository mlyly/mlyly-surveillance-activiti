package fi.zcode.surveillance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initialized by spring.
 *
 * <ul>
 *   <li>deploys sample process: "/testTask.bpmn20.xml"</li>
 *   <li>scheduled start of new process instance every minute</li>
 * </ul>
 *
 *
 * @author mlyly
 */
@Component
@Transactional
public class TaskInitializerBean {

    private static final Logger LOG = LoggerFactory.getLogger(TaskInitializerBean.class);
    private static final String TEST_TASK_CONFIG = "/testTask.bpmn20.xml";
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    public TaskInitializerBean() {
        LOG.info("TaskInitializerBean()...");
        LOG.info("TaskInitializerBean()... done.");
    }

    @PostConstruct
    private void initialize() {
        LOG.info("initialize()...");

        LOG.info("  repositoryService={}", repositoryService);
        LOG.info("  runtimeService={}", runtimeService);
        LOG.info("  taskService={}", taskService);

        if (repositoryService.createDeploymentQuery().deploymentName("helloWorld").count() > 0) {
            LOG.info("  ALREADY DEPLOYED!");
        } else {
            Deployment d = repositoryService
                    .createDeployment()
                    .name("helloWorld")
                    .addClasspathResource(TEST_TASK_CONFIG)
                    .deploy();
            LOG.info("  1 deployment = {}, name = '{}'", d, d.getName());
            LOG.info("  1 deployment = id = {}", d.getId());
        }
        LOG.info("initialize()... done.");
    }

    @Scheduled(fixedRate = 30000L)
    private void printTasks() {
        if (false) {
            LOG.info("printTasks()...");

            LOG.info("  DEPLOYMENTS:");
            for (Deployment deployment : repositoryService.createDeploymentQuery().list()) {
                LOG.info("  deployment = {}", deployment);
                LOG.info("  deployment = time = {}", deployment.getDeploymentTime());
                LOG.info("  deployment.id, .name = {} {}", deployment.getId(), deployment.getName());
            }

            LOG.info("  PROCESS DEFS:");
            List<ProcessDefinition> processes = repositoryService.createProcessDefinitionQuery().list();
            for (ProcessDefinition processDefinition : processes) {
                LOG.info(" process definition = {}", processDefinition);
            }

            LOG.info("  TASKS:");
            List<Task> tasks = taskService.createTaskQuery().list();
            for (Task task : tasks) {
                LOG.info(" task = {}", task);
            }

            LOG.info("printTasks()... done.");
        }
    }
    private int taskCount = 100;

    @Scheduled(fixedRate = 1 * 60 * 1000L)
    private void addTask() {
        if (true) {
            LOG.info("addTask()...");

            Map<String, Object> args = new HashMap<String, Object>();
            args.put("uri", "http://www.zcode.fi/" + taskCount);
            args.put("count", new Integer(0));
            args.put("taskId", new Integer(taskCount++));

            ProcessInstance pi = runtimeService.startProcessInstanceByKey("pollUrlFailOnThree", args);
            LOG.info("  pi = {}", pi);

            LOG.info("addTask()... done.");
        }
    }
}
