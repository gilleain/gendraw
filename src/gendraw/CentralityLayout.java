package gendraw;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import layout.GraphGridLayout;

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
        GraphFileReader graphs = new GraphFileReader(new FileReader(new File(IN_DIR, inputFilename)));
       
        int w = 100;
        int h = 100;
        boolean drawNumbers = false;
        int edgeLen = 30;
        
        ParameterSet params = new ParameterSet();
        params.set("edgeLength", edgeLen);
        params.set("pointRadius", 5);
        params.set("lineWidth", 2);
        if (drawNumbers) {
            params.set("drawNumberLabels", 1);
        } else { 
            params.set("drawNumberLabels", 0);
        }
        params.set("padding", 40);
        
        GraphGridLayout layout = new GraphGridLayout(w, h, params);
        Map<Graph, Representation> reps = layout.layout(graphs); 
        
        Image image = makeBlankImage(layout.getTotalWidth(), layout.getTotalHeight());
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.BLACK);
        for (Graph g : reps.keySet()) {
            Representation rep = reps.get(g);
            if (rep != null) {
                Map<Vertex, Color> colorMap = new CentralityColorer().getColors(g);
                rep.draw(graphics, params, colorMap);
            }
        }
        ImageIO.write((RenderedImage) image, "PNG", getFile(OUT_DIR, outputFilename + ".png"));
    }
}
