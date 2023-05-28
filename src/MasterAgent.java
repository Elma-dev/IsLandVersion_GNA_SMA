import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.List;

public class MasterAgent extends Agent {
    SequentialBehaviour sequentialBehaviour=new SequentialBehaviour();

    DFAgentDescription dfAgentDescription=new DFAgentDescription();
    ServiceDescription serviceDescription=new ServiceDescription();
    HashMap<String,Integer> iceLandAgents=new HashMap<>();



    @Override
    protected void setup() {
        serviceDescription.setType("iceAgent");
        dfAgentDescription.addServices(serviceDescription);
        sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message=new ACLMessage();
                message.setContent("Best");
                try {
                    DFAgentDescription[] agentsDescriptions=DFService.search(this.myAgent,dfAgentDescription);
                    System.out.println(agentsDescriptions.length);
                    for (DFAgentDescription dfAD:agentsDescriptions) {
                        message.addReceiver(dfAD.getName());
                    }
                    send(message);
                } catch (FIPAException e) {
                    throw new RuntimeException(e);

                }
            }
        });

        sequentialBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null){

                    int fitness=Integer.parseInt(message.getContent().split(":")[1]);
                    String results = message.getContent().split(":")[0];
                    iceLandAgents.put(results,fitness);

                }else {
                    block();
                }
            }
        });
        addBehaviour(sequentialBehaviour);
    }
}
