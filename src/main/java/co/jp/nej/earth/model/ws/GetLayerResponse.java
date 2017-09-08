package co.jp.nej.earth.model.ws;

import java.util.ArrayList;
import java.util.List;

import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.Message;

public class GetLayerResponse extends Response {
    private List<Layer> layerResult;

    /**
     *
     */
    public GetLayerResponse() {
        super();
    }

    /**
    *
    */
    public GetLayerResponse(String message) {
        super(message);
    }

    public GetLayerResponse(List<Message> messages) {
        super(messages);
    }

    /**
     * @param doc
     */
    public GetLayerResponse(boolean result, Layer layer) {
        super(result);
        if (layerResult == null) {
            layerResult = new ArrayList<>();
        }
        layerResult.add(layer);
    }

    /**
     * @return the layerResult
     */
    public List<Layer> getLayerResult() {
        return layerResult;
    }

    /**
     * @param layerResult the layerResult to set
     */
    public void setLayerResult(List<Layer> layerResult) {
        this.layerResult = layerResult;
    }
}
