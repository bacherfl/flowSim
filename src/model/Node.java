package model;

import model.cs.ContentStore;
import model.fw.ForwardingStrategy;
import model.pit.PendingInterestTable;
import model.pit.PitEntry;
import model.app.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 12.08.2015.
 */
public class Node {

    private static int nextNodeId = 0;
    private int nextFaceId = 0;

    private int id;

    private List<Face> faces = new ArrayList<>();

    private App app;
    private ForwardingStrategy forwardingStrategy;
    private ContentStore contentStore;
    private PendingInterestTable pit = new PendingInterestTable();

    public Node() {
        id = nextNodeId++;
        Face appFace = new Face();
        addFace(appFace);
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
        this.app.setNode(this);
        new Link(getAppFace(), app.getAppFace(), -1, 0, 1.0);
    }

    public ForwardingStrategy getForwardingStrategy() {
        return forwardingStrategy;
    }

    public void setForwardingStrategy(ForwardingStrategy forwardingStrategy) {
        this.forwardingStrategy = forwardingStrategy;
        this.forwardingStrategy.setNode(this);
        faces.forEach(forwardingStrategy::addFace);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public void onReceiveInterest(Interest interest, Face inFace) {
        PitEntry pitEntry = pit.addPitEntry(interest, inFace);
        if (pitEntry == null) {
            return;
        }
        //TODO lookup in content store
        forwardingStrategy.onInterest(interest, inFace, pitEntry);
    }

    public void onReceiveData(Data data, Face inFace) {
        pit.getPit().get(data.getName()).getInRecords().keySet().forEach(face -> face.sendData(data));
        pit.clearPitEntry(data.getName());
    }

    public void addFace(Face face) {
        face.setFaceId(nextFaceId++);
        face.setNode(this);
        faces.add(face);
        if (forwardingStrategy != null)
            forwardingStrategy.addFace(face);
    }

    public void sendInterest(Interest interest, Face outFace) {
        pit.sentInterest(interest, outFace);
        if (outFace.isAppFace()) {
            app.onInterest(interest);
        } else {
            outFace.sendInterest(interest);
        }
    }

    public void sendData(Data data, Face outFace) {
        outFace.sendData(data);
    }

    public void onDroppedPacket(Packet packet, Face face) {
        forwardingStrategy.onDroppedPacket(packet, face);
    }

    public void onTimedOutInterest(Interest interest, PitEntry pitEntry) {
        forwardingStrategy.onTimedOutInterest(interest, pitEntry);
    }

    public Face getAppFace() {
        return faces.stream().filter(face -> face.getFaceId() == 0).findFirst().get();
    }
}
