package gendraw;

import graph.model.Graph;
import group.Partition;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import distance.CentralityCalculator;

public class MorganPartitioner {
    
    public static Partition morganPartition(Graph g) {
        int n = g.getVertexCount();
        long[] initialValues = new long[n];
        for (int i = 0; i < n; i++) {
            initialValues[i] = g.degree(i);
        }
        initialValues = MorganPartitioner.update(initialValues, g, n);
        return MorganPartitioner.valuesToPartition(initialValues);
    }
    
    public static Partition centralityMorganPartition(Graph g) {
        int n = g.getVertexCount();
        long[] initialValues = new long[n];
        Partition centP = CentralityCalculator.getVertexPartition(g);
        for (int cellIndex = 0; cellIndex < centP.size(); cellIndex++) {
            for (int i : centP.getCell(cellIndex)) {
                initialValues[i] = cellIndex + 1;
            }
        }
        initialValues = MorganPartitioner.update(initialValues, g, n);
        return MorganPartitioner.valuesToPartition(initialValues);
    }
    
    private static Partition valuesToPartition(long[] values) {
        Partition p = new Partition();
        Map<Long, SortedSet<Integer>> cellMap = new HashMap<Long, SortedSet<Integer>>();
        for (int i = 0; i < values.length; i++) {
            long label = values[i];
            SortedSet<Integer> cell;
            if (cellMap.containsKey(label)) {
                cell = cellMap.get(label);
            } else {
                cell = new TreeSet<Integer>();
                cellMap.put(label, cell);
            }
            cell.add(i);
        }
        for (long label : cellMap.keySet()) {
            p.addCell(cellMap.get(label));
        }
        p.order();
        return p;
    }
    
    public static long[] update(long[] values, Graph g, int n) {
        long[] nextValues = new long[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k : g.getConnected(j)) {
                    nextValues[j] += values[k];
                }
            }
//            System.out.println(Arrays.toString(values));
            System.arraycopy(nextValues, 0, values, 0, n);
        }
        return nextValues;
    }
    
}
