package agents.average;

public class Runner {

    public static void main(String[] args) {
        // Start JADE environment
        MyAgentController controller = new MyAgentController();
        controller.startAgents();
    }
}
