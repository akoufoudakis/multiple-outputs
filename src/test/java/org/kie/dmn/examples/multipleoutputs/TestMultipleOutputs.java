package org.kie.dmn.examples.multipleoutputs;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.core.util.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestMultipleOutputs {

    static final Logger LOG = LoggerFactory.getLogger(TestMultipleOutputs.class);

    @Test
    public void testCorrectFile() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = KieHelper.getKieContainer(
                ks.newReleaseId("org.kie.dmn.examples", "multiple-outputs", "1.0"),
                ks.getResources().newClassPathResource("FinalDecisionType.dmn", this.getClass()));

        DMNRuntime runtime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);

        DMNModel dmnModel = runtime.getModel( "http://www.trisotech.com/definitions/_365b96a7-5dab-4633-8baa-8669dc3b71df", "Final decision type" );

        assertNotNull(dmnModel);

        DMNContext context = DMNFactory.newContext();
        context.set("Name submitted", true);
        context.set("Address submitted", true);



        DMNResult result = runtime.evaluateAll(dmnModel, context);
        assertNotNull(result.getDecisionResultByName("Final decision"));

        Map<String, Object> resultMap = (HashMap<String, Object>)result.getDecisionResultByName("Final decision").getResult();
        assertEquals(true, resultMap.get("Complete status"));
    }

    @Test(expected = NullPointerException.class)
    public void testWrongFile() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = KieHelper.getKieContainer(
                ks.newReleaseId("org.kie.dmn.examples", "multiple-outputs", "1.0"),
                ks.getResources().newClassPathResource("FinalDecisionTypeWrong.dmn", this.getClass()));

        DMNRuntime runtime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);

        DMNModel dmnModel = runtime.getModel( "http://www.trisotech.com/definitions/_365b96a7-5dab-4633-8baa-8669dc3b71df", "Final decision type" );

        assertNotNull(dmnModel);

        DMNContext context = DMNFactory.newContext();
        context.set("Name submitted", true);
        context.set("Address submitted", true);



        DMNResult result = runtime.evaluateAll(dmnModel, context);
        assertNotNull(result.getDecisionResultByName("Final decision"));

        Map<String, Object> resultMap = (HashMap<String, Object>)result.getDecisionResultByName("Final decision").getResult();
        assertEquals(true, resultMap.get("Complete status"));
    }

}
