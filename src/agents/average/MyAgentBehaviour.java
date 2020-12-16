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

        // Send our number to all connected agents
        for (String linkedAgent : currentAgent.linkedAgents) {
            AID destination = new AID(linkedAgent, AID.ISLOCALNAME);
            msg.setContent(currentAgent.id.toString() + "___" + currentAgent.number.toString());
            msg.addReceiver(destination);
        }
        currentAgent.send(msg);

        // Look if there is received message in the stack
        ACLMessage receivedMsg = currentAgent.receive();
        if (receivedMsg != null) {
            String[] receivedContent = receivedMsg.getContent().split("___");
            String receivedId = receivedContent[0];
            Double receivedNumber = Double.parseDouble(receivedContent[1]);

            if (!currentAgent.magicTable.containsKey(receivedId)) {
                // We received the number from Agent which is not in our magicTable yet. Remember it.
                currentAgent.magicTable.put(receivedId, receivedNumber);

                // Send this number to neighbours
                for (String linkedAgent : currentAgent.linkedAgents) {
                    AID destination = new AID(linkedAgent, AID.ISLOCALNAME);
                    msg.setContent(receivedMsg.getContent());
                    msg.addReceiver(destination);
                }
                currentAgent.send(msg);
            }
        }

        // After 30 ticks (times) count an average and stop this behaviour
        if (getTickCount() == 30) {
            countAvg();
            currentAgent.doDelete();
            this.stop();
        }
    }

    private void countAvg() {
        MyAgent currentAgent = this.agent;
        double sum = 0.0;
        for (double value : currentAgent.magicTable.values()) {
            sum += value;
        }

        double avg = sum / currentAgent.magicTable.size();
        System.out.println("----" + "\n" +
                "Agent #" + currentAgent.id + "\n" +
                "Avg: " + avg + "\n" +
                "Agent's magicTable: " + currentAgent.magicTable);
    }
}