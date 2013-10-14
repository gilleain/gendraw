package gendraw;

import graph.model.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Minimalist reader of chem3D files to get just connectivity (no xyz, atom symbol, etc...)
 * 
 * @author maclean
 *
 */
public class Chem3DReader {
    
    public static Graph read(String filename) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(filename));
        
        String line;
        int lineCount = 0;
        int atomCount;
        int[][] bondMap = null;
        Graph graph = new Graph();
        while ((line = input.readLine()) != null) {
            if (lineCount == 0) {
                atomCount = Integer.parseInt(line.trim());
                bondMap = new int[atomCount][];
            } else {
                String[] bits = line.trim().split("\\s+");
                int number = Integer.parseInt(bits[1]);
                bondMap[number - 1] = new int[bits.length - 6];
                for (int index = 6; index < bits.length; index++) {
                    int partner = Integer.parseInt(bits[index]) - 1;
                    bondMap[number - 1][index - 6] = partner;
                }
            }
            lineCount++;
        }
        input.close();
        if (bondMap != null) {
            for (int atomIndex = 0; atomIndex < bondMap.length; atomIndex++) {
                for (int partnerIndex : bondMap[atomIndex]) {
                    if (partnerIndex > atomIndex) { // avoid duplicate bonds
                        graph.makeEdge(atomIndex, partnerIndex);
                    }
                }
            }
        }
        return graph;
    }
}
