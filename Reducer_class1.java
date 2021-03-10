import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Reducer_class1 extends Reducer<Adjacent_Word_class, IntWritable, Adjacent_Word_class, DoubleWritable> {
    private DoubleWritable total_count = new DoubleWritable();
    private DoubleWritable relative_count = new DoubleWritable();
    private Text currentWord = new Text("NOT_SET");
    private Text flag = new Text("*");

    protected void reduce(Adjacent_Word_class key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        if (key.getNeighbor().equals(flag)) {
            if (key.getWord().equals(currentWord)) {
                total_count.set(total_count.get() + getTotalCount(values));
            } else {
                currentWord.set(key.getWord());
                total_count.set(0);
                total_count.set(getTotalCount(values));
            }
        } else {
            int count = getTotalCount(values);
            relative_count.set((double) count / total_count.get());
            context.write(key, relative_count);
        }

    }

    private int getTotalCount(Iterable<IntWritable> values) {
        int count = 0;
        for (IntWritable value : values) {
            count += value.get();
        }
        return count;
    }
}
