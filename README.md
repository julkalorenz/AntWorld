# AntWorld Simulation

An ant colony simulation where two competing colonies of ants interact with each other and their environment. Each colony has its own anthill and ants can collect food and attack the enemy ants.

![Ant Simulation](/images/antworld.png)

## World Information
**Ants** - There are two colonies: red and blue. Each colony consists of 20 ants. There are different types of ants in each:

- Red colony:
  - Blunderer - this ant collects food, but while returning home can drop some with certain probability.
  - Collector - collects food from its current position.
  - Soldier - walks around and attacks blue ants.  
- Blue colony:
  - Drone - an ant that walks around the world.
  - Worker - can collect food and attack red ants.

**Objects in the world** - The number of leaves and stones is random, the world is a graph connecting the world objects. Each object has a neighbourhood of objects connected to it. 
- Red anthill & blue anthill - ants departure from there, and after collecting food or encoutering a stone they return on the same path they came from.
- Leaf - has a certain amout of food.
- Stone - if an ant encounters a stone it cannot go through, and it walks back home.

## Technical Details
Simulation imlplemented in Java, using Swing for GUI. 



Instruction:    complie and execute the `WorldFrame` class, 
                the Information Window provides real-time updates about the world and ants, to update the information click the 'Refresh' button.
