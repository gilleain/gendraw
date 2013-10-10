package gendraw;

import graph.model.Graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import planar.Vertex;
import test.draw.BaseDrawTest;
import draw.ParameterSet;
import draw.Representation;

public class CentralityLayout extends BaseDrawTest {
    
    public static final String IN_DIR = "../generate/output/mckay/";
    
    public static final String OUT_DIR = "output/centrality";
    
    @Test
    public void layout4s() throws IOException {
        layout("four_x.txt", "g4");
    }
    
    @Test
    public void layout5s() throws IOException {
        layout("five_x.txt", "g5");
    }
    
    @Test
    public void layout6s() throws IOException {
        layout("six_x.txt", "g6");
    }
    
    @Test
    public void layout7s() throws IOException {
        layout("seven_x.txt", "g7");
    }
    
    public void layout(String inputFilename, String outputFilename) throws IOException {
        GraphFileView view = new GraphFileView(IN_DIR, OUT_DIR);
        view.layout(inputFilename, outputFilename, new DrawAction() {

            @Override
            public boolean perform(Map<Graph, Representation> reps, Graphics2D graphics, ParameterSet params) {
                for (Graph g : reps.keySet()) {
                    Representation rep = reps.get(g);
                    if (rep != null) {
                        Map<Vertex, Color> colorMap = new CentralityColorer().getColors(g);
                        rep.draw(graphics, params, colorMap);
                    }
                }
                return true;
            }
        });
    }
}
