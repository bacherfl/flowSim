package model.fw;

import model.Face;

import java.util.*;

/**
 * Created by florian on 13.08.2015.
 */
public class ForwardingInformationBase {

    List<Face> faces = new ArrayList<>();
    Map<String, List<FibEntry>> fib = new HashMap<>();

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public Map<String, List<FibEntry>> getFib() {
        return fib;
    }

    public void setFib(Map<String, List<FibEntry>> fib) {
        this.fib = fib;
    }

    public void addFibEntry(String prefix, Face face, double cost) {
        FibEntry fibEntry = new FibEntry(face, 0.0);

        List<FibEntry> entries;

        if (!fib.containsKey(prefix)) {
            entries = new ArrayList<>();
            fib.put(prefix, entries);
        } else {
            entries = fib.get(prefix);
        }
        Optional<FibEntry> existingEntry = entries.stream()
                .filter(entry -> entry.getFace().getFaceId() == face.getFaceId())
                .findFirst();

        if (existingEntry.isPresent()) {
            existingEntry.get().setCost(cost);
        } else {
            entries.add(new FibEntry(face, cost));
        }
    }

    public List<FibEntry> getMatchingFibEntries(String prefix) {

        int maxMatch = 0;
        final List<FibEntry>[] ret = new List[]{null};
        fib.keySet().stream().forEach(prefixKey -> {
            String[] prefixKeyArray = prefixKey.split("/");
            String[] prefixArray = prefix.split("%")[0].split("/");

            int match = 0;
            while (((match < prefixArray.length) && (match < prefixKeyArray.length))
                    && (prefixArray[match].equalsIgnoreCase(prefixKeyArray[match]))) {
                match ++;
            }
            if (match > maxMatch) {
                ret[0] = fib.get(prefixKey);
            }
        });
        return ret[0];
    }
}
