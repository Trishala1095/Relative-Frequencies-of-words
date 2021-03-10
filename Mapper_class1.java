import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class Mapper_class1 extends Mapper<LongWritable, Text, Adjacent_Word_class, IntWritable> {
    private Adjacent_Word_class word_Pair = new Adjacent_Word_class();
    private IntWritable ONE = new IntWritable(1);
    private IntWritable word_Frequencies = new IntWritable();

    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        int neighbours_value = context.getConfiguration().getInt("neighbours_value", 1);
        String[] words = value.toString().split("\\s+");
        if (words.length > 1) {
            for (int i = 0; i < words.length; i++) {
                if(words[i].matches("^[A-Za-z]+$")) {
                    words[i] = words[i].replaceAll("\\W+","");

                    if(words[i].equals("")){
                        continue;
                    }

                    word_Pair.setWord(words[i].toLowerCase());

                    int start = 0;
                    if(i > neighbours_value )
                    {
                       start = i - neighbours_value;
                    }

                    int end = i + neighbours_value;
                    if(i + neighbours_value >= words.length)
                    {
                        end = words.length - 1;
                    }

                    for (int j = start; j <= end; j++) {
                        if (j == i) continue;
                        if(words[j].matches("^[A-Za-z]+$")) {
                          words[j] = words[j].replaceAll("\\W","");
                          word_Pair.setNeighbor(words[j].toLowerCase());
                          context.write(word_Pair, ONE);
                        }
                    }

                    word_Pair.setNeighbor("*");
                    word_Frequencies.set(end - start);
                    context.write(word_Pair, word_Frequencies);
                }
            }
        }
    }
}
