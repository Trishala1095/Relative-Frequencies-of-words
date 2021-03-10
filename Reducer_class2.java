import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Reducer_class2 extends Reducer<DoubleWritable,Adjacent_Word_class,Adjacent_Word_class,DoubleWritable> {
    private int i = 0;
    
    protected void reduce(DoubleWritable key, Iterable<Adjacent_Word_class> values, Context context) throws IOException, InterruptedException {
       
        for (Adjacent_Word_class value : values) {

          if(i >= 100)
              break;

          if(key.get() == 1.0)
             continue;

          context.write(value,key);
          i++;
        }
    }
}
