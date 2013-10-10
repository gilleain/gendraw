package gendraw;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import layout.GraphGridLayout;
import draw.ParameterSet;
import draw.Representation;

public class GraphFileView {
    
    private String IN_DIR = "../generate/output/mckay/";
    
    private String OUT_DIR = "output/centrality";
    
    public GraphFileView(String inDir, String outDir) {
        this.IN_DIR = inDir;
        this.OUT_DIR = outDir;
    }
    
    public void layout(Iterable<Graph> graphs, String outputFilename, DrawAction action) throws IOException {
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

        // carry out some action on the set of representations
        
        if (action.perform(reps, graphics, params)) {
            ImageIO.write((RenderedImage) image, "PNG", getFile(OUT_DIR, outputFilename + ".png"));
        }
    }
    
    public void layout(String inputFilename, String outputFilename, DrawAction action) throws IOException {
        layout(new GraphFileReader(
                    new FileReader(
                            new File(IN_DIR, inputFilename))),
                 outputFilename, action);
    }
    
    public Image makeBlankImage(int w, int h) {
        Image img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        img.getGraphics().setColor(Color.WHITE);
        img.getGraphics().fillRect(0, 0, w, h);
        return img;
    }
    
    public File getFile(File dir, String filename) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, filename);
    }
    
    public File getFile(String OUT_DIR, String filename) {
        return getFile(new File(OUT_DIR), filename);
    }
    
}
