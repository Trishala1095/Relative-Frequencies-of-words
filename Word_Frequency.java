import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class Word_Frequency {

    public static void main(String[] args) throws IOException,InterruptedException,ClassNotFoundException {

        //1st series of mapreduce jobs
    	Configuration config1=new Configuration();
        Job jobs = Job.getInstance(config1,"Wordpair-frequency");
        jobs.setJarByClass(Word_Frequency.class);

        FileInputFormat.addInputPath(jobs, new Path(args[0]));
        FileOutputFormat.setOutputPath(jobs, new Path("output_value"));

        jobs.setMapperClass(Mapper_class1.class);
        jobs.setReducerClass(Reducer_class1.class);
        jobs.setCombinerClass(Adjacent_Word_Reducer_class.class);
        jobs.setPartitionerClass(Adjacent_Word_Partitioner_class.class);
        jobs.setNumReduceTasks(3);

        jobs.setOutputKeyClass(Adjacent_Word_class.class);
        jobs.setOutputValueClass(IntWritable.class);
        boolean b_value = jobs.waitForCompletion(true);
        if (!b_value) {
       	throw new IOException("Error with mapreduce jobs!");}

        //2nd series of mapreduce jobs       
    	Configuration config2=new Configuration();
        Job jobs1 = Job.getInstance(config2,"top100-wordpair");
        jobs1.setJarByClass(Word_Frequency.class);
        jobs1.setJarByClass(Word_Frequency.class);
        
        jobs1.setSortComparatorClass(Descending_Key_class.class);
        FileInputFormat.addInputPath(jobs1, new Path("output_value"));
        FileOutputFormat.setOutputPath(jobs1, new Path(args[1]));

        jobs1.setMapperClass(Mapper_class2.class);
        jobs1.setReducerClass(Reducer_class2.class);
        jobs1.setNumReduceTasks(1);

        jobs1.setOutputKeyClass(DoubleWritable.class);
        jobs1.setOutputValueClass(Adjacent_Word_class.class);
        boolean b_value1 = jobs1.waitForCompletion(true);
        if (!b_value1) {
       	throw new IOException("Error with mapreduce jobs!");}
    }
}
