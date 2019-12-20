package no.entra.bacnet.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO not very happy with this design
 */
public class ObservationList implements BacnetMessage {

    private List<Observation> observations = new ArrayList<>();

    public ObservationList() {
    }

    public ObservationList(List<Observation> observations) {
        this.observations = observations;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public void addObservation(Observation observation) {
        observations.add(observation);
    }

    @Override
    public String toJson() {
        return asJsonObject().toString();
    }

    @Override
    public JSONObject asJsonObject() {

        JSONObject json = new JSONObject();
        JSONArray observationsJson = new JSONArray();
        for (Observation observation : observations) {
            observationsJson.put(observation.toJson());
        }
        json.put("observations", observationsJson);

        return json;
    }
}
