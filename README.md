# AntWorld Simulation

An ant colony simulation where two competing colonies of ants interact with each other and their environment. Each colony has its own anthill and ants can collect food and attack the enemy ants.

**Types of ants:**

Red colony:
- Blunderer - this ant collects food, but while returning home can drop some with certain probability.
- Collector - collects food from its current position.
- Soldier - walks around and attacks blue ants.
  
Blue colony:
- Drone - an ant that walks around the world.
- Worker - can collect food and attack red ants.

**Object in the world:**
- Red anthill and blue anthill - ants departure from there, and after collecting food or encoutering a stone they return on the same path they came from.
- Leaf - has a certain amout of food.
- Stone - if an ant encounters a stone it cannot go through, and it walks back home.

![Ant Simulation](/images/antworld.png)



Instruction:    to run the program just run the `WorldFrame` class, 
                the Information Window contains the information about the world and ants to update the information press the 'Refresh' button.
