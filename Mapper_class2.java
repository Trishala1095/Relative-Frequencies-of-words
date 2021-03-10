import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.*;
import java.io.IOException;

 
public class Mapper_class2
       extends Mapper<Object, Text, DoubleWritable, Adjacent_Word_class>{

        private String[] str;
        private String[] tokens;
        private DoubleWritable relative_Frequency = new DoubleWritable();
 
        public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

           StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
           while (itr.hasMoreTokens()) {
      	      tokens = itr.nextToken().toString().split("\t");
      	      str = tokens[0].toString().split(" ");
              Adjacent_Word_class word_Pair = new Adjacent_Word_class(str[0], str[1]);
      	      relative_Frequency.set(Double.parseDouble(tokens[1].trim()));
              if(relative_Frequency == null)
                  continue;
              context.write(relative_Frequency, word_Pair);
          }
        }
}
