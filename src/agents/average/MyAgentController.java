package agents.average;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

class MyAgentController {

    private static final int numberOfAgents = 5;

    void startAgents() {
        // Some setup routine...
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);

        // Let's start our agents
        try {
            for (int i = 1; i <= MyAgentController.numberOfAgents; i++) {
                AgentController agent = cc.createNewAgent(Integer.toString(i), "agents.average.MyAgent", null);
                agent.start();
            }
        } catch (StaleProxyException e) {
            System.out.println("FAILED to start agents!");
            e.printStackTrace();
        }
    }
}