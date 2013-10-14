package gendraw;

import java.io.IOException;

import graph.model.Graph;

import org.junit.Test;

public class Chem3DReaderTest {
    
    @Test
    public void read() throws IOException {
        String filename = "/Users/maclean/Documents/molecules/FullereneLib/C180.cc1";
        Graph g = Chem3DReader.read(filename);
        System.out.println(g.getVertexCount());
        for (int i = 0; i < g.getVertexCount(); i++) {
            System.out.println(i + " " + g.degree(i));
        }
    }
    
}
