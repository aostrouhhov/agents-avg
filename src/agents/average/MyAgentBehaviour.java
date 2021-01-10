package agents.average;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class MyAgentBehaviour extends TickerBehaviour {
    private final MyAgent agent;

    MyAgentBehaviour(MyAgent agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;
    }

    @Override
    protected void onTick() {
        MyAgent currentAgent = this.agent;

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        for (String agent : currentAgent.linkedAgents) {
            AID destination = new AID(agent, AID.ISLOCALNAME);
            msg.addReceiver(destination);
        }

        try {
            // Noisy measurements
            msg.setContentObject(currentAgent.stateValue + Math.random() * 0.2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentAgent.send(msg);
        currentAgent.communicators.clear();

        while (currentAgent.communicators.size() != currentAgent.linkedAgents.length) {
            ACLMessage msgRes = currentAgent.receive();
            if (msgRes != null) {
                try {
                    Double content = (Double) msgRes.getContentObject();
                    AID agentSender = msgRes.getSender();

                    // Probability of broken link between 2 and 3 is 30%
                    if (Math.random() <= 0.3 &&
                            (agentSender.getLocalName().equals("2") && currentAgent.id == 3 ||
                                    agentSender.getLocalName().equals("3") && currentAgent.id == 2)) {
                        currentAgent.communicators.put(agentSender, 0.0);
                    } else if (content != null) {
                        currentAgent.communicators.put(agentSender, (currentAgent.stateValue - content) * 0.1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        currentAgent.communicators.forEach((k, v) -> currentAgent.stateValue -= v);

        if (getTickCount() == 14) {
            System.out.println("Agent: " + currentAgent.getLocalName() +
                    " counted Avg: " + currentAgent.stateValue);

            currentAgent.doDelete();
            this.stop();
        }
    }
}