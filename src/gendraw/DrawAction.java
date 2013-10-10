package gendraw;

import graph.model.Graph;

import java.awt.Graphics2D;
import java.util.Map;

import draw.ParameterSet;
import draw.Representation;

public interface DrawAction {
    
    public boolean perform(
            Map<Graph, Representation> map, Graphics2D graphics, ParameterSet params);
    
}
