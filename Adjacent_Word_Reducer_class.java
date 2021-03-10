import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Adjacent_Word_Reducer_class extends Reducer<Adjacent_Word_class,IntWritable,Adjacent_Word_class,IntWritable> {
    private IntWritable totalCount = new IntWritable();

    @Override
    protected void reduce(Adjacent_Word_class key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
             count += value.get();
        }
        totalCount.set(count);
        context.write(key,totalCount);
    }
}
