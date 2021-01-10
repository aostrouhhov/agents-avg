# About

- Group of 5 agents.
- Each agent stores scalar value.
- Their task is to calculate the arithmetic mean in a distributed way using Local Voting Protocol.

Also:
- Each agent receives noisy measurements from their neighbors.
- The links between agents can switch from their active state to inactive and backward.


# Build and Run

Just execute the following commands:
```
$ javac -d . -cp "libs/jade.jar" src/agents/average/*.java
$ java -cp "libs/jade.jar:." agents/average/Runner
```
