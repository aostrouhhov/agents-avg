package agents.average;

import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MyAgent extends Agent {
    public Integer id;
    public Double number;
    public Double stateValue;
    // 'communicators' hashmap is for Local Voting Algorithm
    public HashMap<AID, Double> communicators = new HashMap<>();
    public String[] linkedAgents;
    public HashMap<String, Double> magicTable = new HashMap<>();

    // Describe connections between Agents in this Class just for simplicity
    // In production environment these connections can be obtained differently by Agent
    // Graph: 1-2-3
    //        | |
    //        4 5
    private static final ArrayList<String[]> linksGraph = new ArrayList<>(Arrays.asList(
            new String[]{"2", "4"},
            new String[]{"1", "3", "5"},
            new String[]{"2"},
            new String[]{"1"},
            new String[]{"2"}
    ));

    @Override
    protected void setup() {
        this.id = Integer.parseInt(getAID().getLocalName());
        // Set current agent's neighbours
        this.linkedAgents = linksGraph.get(id - 1);
        this.number = this.id.doubleValue();
        this.stateValue = this.number;

        System.out.println("Setup done, started Agent # " + id);

        // Start the algorithm
        this.magicTable.put(this.id.toString(), this.number);
        this.doWait(500);
        addBehaviour(new MyAgentBehaviour(this, 100));
    }
}
