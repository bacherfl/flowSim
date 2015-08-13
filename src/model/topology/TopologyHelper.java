package model.topology;

import model.Face;
import model.Link;
import model.Node;

/**
 * Created by florian on 12.08.2015.
 */
public class TopologyHelper {

    public void addLink(Node n1, Node n2) {
        Face f1 = new Face();
        Face f2 = new Face();
        new Link(f1, f2);
        n1.addFace(f1);
        n2.addFace(f2);
    }

    public void addLink(Node n1, Node n2, int bandwidth, int delay, double reliability) {
        Face f1 = new Face();
        Face f2 = new Face();
        new Link(f1, f2, bandwidth, delay, reliability);
        n1.addFace(f1);
        n2.addFace(f2);
    }
}
