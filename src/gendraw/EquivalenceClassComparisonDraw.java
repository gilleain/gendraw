package gendraw;

import graph.model.Graph;
import graph.model.GraphFileReader;
import group.Partition;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import planar.Vertex;
import test.draw.BaseDrawTest;
import distance.CentralityCalculator;
import draw.ParameterSet;
import draw.Representation;

public class EquivalenceClassComparisonDraw extends BaseDrawTest {
    
    public static final String IN_DIR = "../generate/output/mckay/";
    
    public static final String OUT_DIR = "output/compare";
    
    @Test
    public void sixes() throws IOException {
        draw("six_x.txt", "sixes");
    }
    
    @Test
    public void sevens() throws IOException {
        draw("seven_x.txt", "sevens");
    }
    
    @Test
    public void eights() throws IOException {
        draw("eight_x.txt", "eights");
    }
    
    public void draw(String inputFilename, String outputFilename) throws IOException {
        List<Graph> graphs = GraphFileReader.readAll(new File(IN_DIR, inputFilename).toString());
        List<Graph> fails = new ArrayList<Graph>();
        for (Graph g : graphs) {
            Partition pCent = CentralityCalculator.getVertexPartition(g);
            Partition pSig = SignaturePartitioner.getSigPartition(g);
            if (!pCent.equals(pSig)) {
                System.out.println(pCent + "\n" + pSig + "\n" + g + "\n-------------\n");
                fails.add(g);
            }
        }
        
        GraphFileView view = new GraphFileView(IN_DIR, OUT_DIR, false);
        view.layout(fails, outputFilename, new DrawAction() {
            
            @Override
            public boolean perform(Map<Graph, Representation> reps, Graphics2D graphics, ParameterSet params) {
                List<Graph> nonNulls = new ArrayList<Graph>();
                for (Graph g : reps.keySet()) {
                    Representation rep = reps.get(g);
                    if (rep != null) nonNulls.add(g);
                }
                for (Graph g : nonNulls) {
                    Representation rep = reps.get(g);
                    Map<Vertex, Color> colorMap = new CentralityColorer().getColors(g);
                    rep.draw(graphics, params, colorMap);
                }
                return true;
            }
        });
    }
   
}
