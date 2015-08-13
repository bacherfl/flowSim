package model.fw;

import model.Face;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 13.08.2015.
 */
public class ForwardingInformationBase {

    List<Face> faces = new ArrayList<>();

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }
}
