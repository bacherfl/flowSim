package scenarios;

import extensions.strategies.BroadcastStrategy;
import extensions.strategies.app.SimpleConsumer;
import extensions.strategies.app.SimpleProducer;
import model.app.App;
import model.topology.NodeContainer;
import model.topology.TopologyHelper;
import sim.Simulator;

/**
 * Created by florian on 12.08.2015.
 */
public class Hello {

    public static void main(String[] args) {
        Simulator s = Simulator.getInstance();

        NodeContainer nc = new NodeContainer(4);
        nc.getNodes().forEach(node -> {
            App app;
            if (node.getId() == 0) {
                app = new SimpleConsumer(true, "/name");
            } else if (node.getId() == 2) {
                app = new SimpleConsumer(true, "/name");
            } else if(node.getId() == 3) {
                app = new SimpleProducer("/name");
            }
            else {
                app = new SimpleConsumer();
            }
            if (node.getId() == 2)
                app.startAt(450);
            else
                app.startAt(1);
            node.setApp(app);
            node.setForwardingStrategy(new BroadcastStrategy());
        });

        TopologyHelper th = new TopologyHelper();

        th.addLink(nc.getNodes().get(0), nc.getNodes().get(1));
        th.addLink(nc.getNodes().get(0), nc.getNodes().get(2));
        th.addLink(nc.getNodes().get(1), nc.getNodes().get(3));
        th.addLink(nc.getNodes().get(1), nc.getNodes().get(2));

        s.setSimulationLengthInTenthMilliSeconds(4000L);
        s.start();
    }
}
