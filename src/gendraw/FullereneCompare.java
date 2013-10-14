package gendraw;

import graph.model.Graph;
import group.Partition;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import distance.CentralityCalculator;

public class FullereneCompare {
    
    public void summary(Partition p) {
        System.out.print("{");
        for (int c = 0; c < p.size(); c++) {
            System.out.print(p.getCell(c).size() + "|");
            if (c < p.size() - 1) {
                System.out.print("|");
            }
        }
        System.out.println("}");
    }
    
    @Test
    public void testC240() throws IOException {
        testGraph("/Users/maclean/Documents/molecules/FullereneLib/C240.cc1");
    }
    
    @Test
    public void testC180() throws IOException {
        testGraph("/Users/maclean/Documents/molecules/FullereneLib/C180.cc1");
    }
    
    @Test
    public void testC20_30() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C20-30");
    }
    
    @Test
    public void testC32() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C32");
    }
    
    @Test
    public void testC34() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C34");
    }
    
    @Test
    public void testC36() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C36");
    }
    
    @Test
    public void testC38() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C38");
    }
    
    @Test
    public void testC40() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C40");
    }
    
    @Test
    public void testC42() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C42");
    }
    
    @Test
    public void testC44() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C44");
    }
    
    @Test
    public void testC46() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C46");
    }
    
    @Test
    public void testC60_76() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C60-76");
    }
    
    @Test
    public void testC78() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C78");
    }
    
    @Test
    public void testC80() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C80");
    }
    
    @Test
    public void testC90() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C90");
    }
    
    @Test
    public void testC92() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C92");
    }
    
    @Test
    public void testC94() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C94");
    }
    
    @Test
    public void testC96() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C96");
    }
    
    @Test
    public void testC98() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C98");
    }
    
    @Test
    public void testC100() throws IOException {
        testDir("/Users/maclean/Documents/molecules/FullereneLib/C100");
    }
    
    public void testDir(String dir) throws IOException {
        for (String filename : new File(dir).list()) {
            if (filename.endsWith("cc1")) {
                testGraph(new File(dir, filename).toString());
            }
        }
    }
    
    public void testGraph(String filename) throws IOException {
        Graph g; 
        try {
            g = Chem3DReader.read(filename);
        } catch (Exception e) {
            System.out.println("exception " + filename);
            return;
        }
        Partition sigPartition = SignaturePartitioner.getSigPartition(g);
        Partition cenPartition = CentralityCalculator.getVertexPartition(g);
//        Partition mixMoPartition = MorganPartitioner.centralityMorganPartition(g);
        summary(sigPartition);
        summary(cenPartition);
//        summary(mixMoPartition);
        if (sigPartition.equals(cenPartition)) {
//        if (sigPartition.equals(mixMoPartition)) {
            System.out.println("EQUAL " + filename);
        } else {
            System.out.println("NEQUAL " + filename);
        }
    }
    
}
