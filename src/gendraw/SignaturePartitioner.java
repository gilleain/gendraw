package gendraw;

import graph.model.Graph;
import graph.model.GraphSignature;
import group.Partition;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import signature.SymmetryClass;

public class SignaturePartitioner {
    
    public static Partition getSigPartition(Graph g) {
        GraphSignature sig = new GraphSignature(g);
        List<SymmetryClass> o = sig.getSymmetryClasses();
        Collections.sort(o, new Comparator<SymmetryClass>() {

            public int compare(SymmetryClass o0, SymmetryClass o1) {
                Integer f0 = o0.iterator().next();
                Integer f1 = o1.iterator().next();
                return f0.compareTo(f1);
            }
            
        });
        
        Partition p = new Partition();
        for (SymmetryClass sc : o) {
            SortedSet<Integer> cell = new TreeSet<Integer>();
            for (Integer i : sc) {
                cell.add(i);
            }
            p.addCell(cell);
        }
        return p;
    }
    
}
