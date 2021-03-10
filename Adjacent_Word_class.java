import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Adjacent_Word_class implements Writable,WritableComparable<Adjacent_Word_class> {

    private Text word;
    private Text neighbours_value;

    public Adjacent_Word_class(Text word, Text neighbours_value) {
        this.word = word;
        this.neighbours_value = neighbours_value;
    }

    public Adjacent_Word_class(String word, String neighbours_value) {
        this(new Text(word),new Text(neighbours_value));
    }

    public Adjacent_Word_class() {
        this.word = new Text();
        this.neighbours_value = new Text();
    }

    @Override
    public int compareTo(Adjacent_Word_class other) {
        int returnVal = this.word.compareTo(other.getWord());
        if(returnVal != 0){
            return returnVal;
        }
        if(this.neighbours_value.toString().equals("*")){
            return -1;
        }else if(other.getNeighbor().toString().equals("*")){
            return 1;
        }
        return this.neighbours_value.compareTo(other.getNeighbor());
    }

    public static Adjacent_Word_class read(DataInput in) throws IOException {
        Adjacent_Word_class word_Pair = new Adjacent_Word_class();
        word_Pair.readFields(in);
        return word_Pair;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        word.write(out);
        neighbours_value.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        word.readFields(in);
        neighbours_value.readFields(in);
    }

    @Override
    public String toString() {
          return (word + " " + neighbours_value); 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adjacent_Word_class word_Pair = (Adjacent_Word_class) o;

        if (neighbours_value != null ? !neighbours_value.equals(word_Pair.neighbours_value) : word_Pair.neighbours_value != null) return false;
        if (word != null ? !word.equals(word_Pair.word) : word_Pair.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 163 * result + (neighbours_value != null ? neighbours_value.hashCode() : 0);
        return result;
    }

    public void setWord(String word){
        this.word.set(word);
    }
    public void setNeighbor(String neighbours_value){
        this.neighbours_value.set(neighbours_value);
    }

    public Text getWord() {
        return word;
    }

    public Text getNeighbor() {
        return neighbours_value;
    }
}
