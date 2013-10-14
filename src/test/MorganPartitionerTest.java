package test;

import junit.framework.Assert;
import gendraw.MorganPartitioner;
import graph.model.Graph;
import group.Partition;

import org.junit.Test;

public class MorganPartitionerTest {
    
    @Test
    public void testClaw_pure() {
        testPureMorganPartitionOfGraph(
                new Graph("0:1,0:2,0:3,1:2"), Partition.fromString("0|1,2|3"));
    }
    
    @Test
    public void testRabbitSquare_pure() {
        testPureMorganPartitionOfGraph(
                new Graph("0:1,0:3,0:4,1:2,1:5,2:3"), Partition.fromString("0,1|2,3|4,5"));
    }
    
    @Test
    public void testClaw_mixed() {
        testMixedMorganPartitionOfGraph(
                new Graph("0:1,0:2,0:3,1:2"), Partition.fromString("0|1,2|3"));
    }
    
    @Test
    public void testRabbitSquare_mixed() {
        testMixedMorganPartitionOfGraph(
                new Graph("0:1,0:3,0:4,1:2,1:5,2:3"), Partition.fromString("0,1|2,3|4,5"));
    }
    
    @Test
    public void testCuneane_pure() {
        testPureMorganPartitionOfGraph(
                new Graph("0:1,0:3,0:5,1:2,1:7,2:3,2:7,3:4,4:5,4:6,5:6,6:7"), Partition.fromString("0,1,2,3,4,5,6,7"));
    }
    
    @Test
    public void testCuneane_mixed() {
        testMixedMorganPartitionOfGraph(
                new Graph("0:1,0:3,0:5,1:2,1:7,2:3,2:7,3:4,4:5,4:6,5:6,6:7"), Partition.fromString("0,3|1,2,4,5|6,7"));
    }
    
    public void testPureMorganPartitionOfGraph(Graph g, Partition expected) {
        Partition actual = MorganPartitioner.morganPartition(g);
        Assert.assertEquals(expected, actual);
    }
    
    public void testMixedMorganPartitionOfGraph(Graph g, Partition expected) {
        Partition actual = MorganPartitioner.centralityMorganPartition(g);
        Assert.assertEquals(expected, actual);
    }
    
}
