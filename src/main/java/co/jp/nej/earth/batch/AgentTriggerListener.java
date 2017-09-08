package co.jp.nej.earth.batch;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

public class AgentTriggerListener extends TriggerListenerSupport {

    private static final String NAME_LISTENER = "AgentListener";

    private String name = NAME_LISTENER;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context){
        System.out.println("go AgentTriggerListener");
    }

}
