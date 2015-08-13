package scenarios;

import extensions.strategies.BroadcastStrategy;
import extensions.strategies.app.SimpleConsumer;
import model.topology.NodeContainer;
import model.topology.TopologyHelper;
import sim.Scheduler;
import sim.Simulator;

/**
 * Created by florian on 12.08.2015.
 */
public class Hello {

    public static void main(String[] args) {
        Simulator s = Simulator.getInstance();

        NodeContainer nc = new NodeContainer(2);
        nc.getNodes().forEach(node -> {
            SimpleConsumer simpleConsumer = new SimpleConsumer();
            if (node.getId() == 1)
                simpleConsumer.setConsume(false);
            simpleConsumer.startAt(1);
            node.setApp(simpleConsumer);
            node.setForwardingStrategy(new BroadcastStrategy());
        });

        TopologyHelper th = new TopologyHelper();

        th.addLink(nc.getNodes().get(0), nc.getNodes().get(1));

        s.setSimulationLengthInMilliSeconds(40L);
        s.start();
    }
}
