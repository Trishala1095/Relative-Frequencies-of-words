import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class Adjacent_Word_Partitioner_class extends Partitioner<Adjacent_Word_class,IntWritable> {

    @Override
    public int getPartition(Adjacent_Word_class word_Pair, IntWritable intWritable, int numPartitions) {
        return (word_Pair.getWord().hashCode() & Integer.MAX_VALUE ) % numPartitions;
    }
}
