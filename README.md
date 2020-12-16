# About

We have an environment with 5 Agents. Each Agent has a personal number (equals to Agent's ID).
We need to count an average of all Agents' numbers. Each Agent has its own 'magicTable' where it stores received IDs and Numbers.

1. Write own ID and Number to the magicTable.
2. Send own ID and Number to all connected Agents.
3. Look at the received message: ID and Number.
	* If magicTable contains this ID then do nothing.
	* If magicTable doesn't contain this ID then put received ID and Number there and send these ID and Number as a message to connected Agents.
4. Execute steps 2 and 3 for 30 times.

# Build and Run

Just execute the following commands:
```
$ javac -d . -cp "libs/jade.jar" src/agents/average/*.java
$ java -cp "libs/jade.jar:." agents/average/Runner
```
