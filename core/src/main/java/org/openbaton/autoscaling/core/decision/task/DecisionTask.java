package org.openbaton.autoscaling.core.decision.task;

import org.openbaton.autoscaling.core.decision.DecisionManagement;
import org.openbaton.autoscaling.core.detection.DetectionEngine;
import org.openbaton.autoscaling.core.execution.ExecutionManagement;
import org.openbaton.catalogue.mano.common.AutoScalePolicy;
import org.openbaton.catalogue.mano.record.VirtualNetworkFunctionRecord;
import org.openbaton.exceptions.NotFoundException;
import org.openbaton.sdk.NFVORequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by mpa on 27.10.15.
 */

@Service
@Scope("prototype")
public class DecisionTask implements Runnable {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private Properties properties;

    private String nsr_id;

    private String vnfr_id;

    private AutoScalePolicy autoScalePolicy;

    private String name;

    @Autowired
    private DecisionManagement decisionManagement;

    public DecisionTask(String nsr_id, String vnfr_id, AutoScalePolicy autoScalePolicy, Properties properties) {
        this.properties = properties;
        this.nsr_id = nsr_id;
        this.vnfr_id = vnfr_id;
        this.autoScalePolicy = autoScalePolicy;
        this.name = "DecisionTask#" + nsr_id + ":" + vnfr_id;
    }


    @Override
    public void run() {
        decisionManagement.sendToExecutor(nsr_id, vnfr_id, autoScalePolicy.getActions());
    }
}