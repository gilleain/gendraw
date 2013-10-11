package gendraw;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import planar.Vertex;
import test.draw.BaseDrawTest;
import distance.CentralityCalculator;
import draw.ParameterSet;
import draw.Representation;

public class ORSEquivalenceClassDraw extends BaseDrawTest {
    
    public static final String IN_DIR = "../generate/output/mckay/";
    
    public static final String OUT_DIR = "output/eqcl";
    
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
    
    public void draw(String inputFilename, String outputPrefix) throws IOException {
        List<Graph> graphs = GraphFileReader.readAll(new File(IN_DIR, inputFilename).toString());
        Map<String, List<Graph>> orsEqCl = new HashMap<String, List<Graph>>();
        for (Graph g : graphs) {
            int[] ors = CentralityCalculator.getORS(g);
            String orsString = Arrays.toString(ors); 
            List<Graph> eqCl;
            if (orsEqCl.containsKey(orsString)) {
                eqCl = orsEqCl.get(orsString);
            } else {
                eqCl = new ArrayList<Graph>();
                orsEqCl.put(orsString, eqCl);
            }
            eqCl.add(g);
        }
        
        GraphFileView view = new GraphFileView(IN_DIR, OUT_DIR, false);
        int counter = 0;
        List<String> keys = new ArrayList<String>(orsEqCl.keySet());
//        Collections.sort(keys, new LexIntArrComparator());
        Collections.sort(keys);
//        for (int[] ors : keys) {
        for (String ors : keys) {
            List<Graph> eqCl = orsEqCl.get(ors);
//            System.out.println(eqCl.size() + "\t" + Arrays.toString(ors) + "\t" + eqCl);
            if (eqCl.size() > 1) {
                System.out.println(counter + "\t" + eqCl.size() + "\t" + ors + "\t" + eqCl);
                String outputFilename = outputPrefix + "eqCl" + counter;
                view.layout(eqCl, outputFilename, new DrawAction() {
                    
                    @Override
                    public boolean perform(Map<Graph, Representation> reps, Graphics2D graphics, ParameterSet params) {
                        List<Graph> nonNulls = new ArrayList<Graph>();
                        for (Graph g : reps.keySet()) {
                            Representation rep = reps.get(g);
                            if (rep != null) nonNulls.add(g);
                        }
                        if (nonNulls.size() < 2) return false;
                        for (Graph g : nonNulls) {
                            Representation rep = reps.get(g);
                            Map<Vertex, Color> colorMap = new CentralityColorer().getColors(g);
                            rep.draw(graphics, params, colorMap);
                        }
                        return true;
                    }
                });
                counter++;
            }
        }
    }
}
