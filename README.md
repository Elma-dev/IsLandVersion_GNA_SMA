# IsLandVersion_GNA_SMA
```
Another approach to implementing GNA utilizing a distributed system is theIsLand Version of GNA in System Multi Agents.
This approach works really well and produces results.
With this approach, we divide the problem into smaller ones and have each agent try to come up with the best solution.
The working approach is different between this method and the first one (https://github.com/Elma-dev/GA_using_SMA) (see the schema).
```
# 🌳 Project Structure
```
├── IsLandAgent.java
├── Main.java
├── MainContainer.java
├── MasterAgent.java
├── MasterContainer.java
├── sequencial
└── SimpleContainer.java

```

```
├── AGApp.java
├── GAUtils.java
├── Individual.java
└── Population.java

```
# 🕸 Schema of IsLandVersion
![gna](https://github.com/Elma-dev/IsLandVersion_GNA_SMA/assets/67378945/56a2881b-ca14-4673-9038-ab79fee2ffaf)


# 🏝IsLand Operations

In this [repository](https://github.com/Elma-dev/GA_using_SMA), each Operation has a description.

![operation](https://github.com/Elma-dev/IsLandVersion_GNA_SMA/assets/67378945/e69454de-197a-4be9-aa26-571db160ec9a)

# IsLand Code
```
public class IsLandAgent extends Agent {
    Population population=new Population();
    DFAgentDescription dfAgentDescription=new DFAgentDescription();
    ServiceDescription serviceDescription=new ServiceDescription();
    @Override
    protected void setup() {
        dfAgentDescription=new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        serviceDescription=new ServiceDescription();
        serviceDescription.setType("iceAgent");
        serviceDescription.setName("ga_ma");
        dfAgentDescription.addServices(serviceDescription);

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    DFService.register(myAgent,dfAgentDescription);
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
                population.initialaizePopulation();
                population.calculateIndFintess();
                population.sortPopulation();
                int it=0;


                while (it<GAUtils.MAX_IT && population.getFitnessIndivd().getFitness()<GAUtils.CHROMOSOME_SIZE){
                    population.selection();
                    population.crossover();
                    population.mutation();
                    population.calculateIndFintess();
                    population.sortPopulation();
                    it++;
                }
                ACLMessage message=blockingReceive();
                if (message!= null) {
                    ACLMessage replay=new ACLMessage();
                    replay.addReceiver(message.getSender());
                    Individual best = population.getFitnessIndivd();
                    replay.setContent(String.valueOf(best.getGenes()) +":"+best.getFitness());
                    send(replay);
                }
            }
        });
    }
}

```
# Why DF Agent (a little explanation)
![image](https://github.com/Elma-dev/IsLandVersion_GNA_SMA/assets/67378945/3fe8df8d-9155-4b32-b3a2-da5fcdfca62a)

