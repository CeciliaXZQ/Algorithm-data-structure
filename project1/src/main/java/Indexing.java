import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceciliaX on 01/12/16.
 */
public class Indexing {

    public ArrayList<ArrayList<WordAttribute>> words = new ArrayList();

     //This makes indexing really fast when the same word occurs many times because
     //the same word does not have to be sorted multiple times.


    class WordAttribute {
        public Word word;
        public List<AttributeCount> attributecount = new ArrayList<AttributeCount>();

        class AttributeCount {
            public Attributes attributes;
            public int count;

            public AttributeCount(Attributes attributes,int count) {
                this.attributes = attributes;
                this.count=count;
            }
           /* public int getcount()
            {
                return this.count;
            }*/
        }

        public WordAttribute (Word word,Attributes attributes,int count) {
            this.word = word;
            AttributeCount ac = new  AttributeCount(  attributes,count) ;
            this.attributecount.add(ac);
        }
    }

    public void addWord(Word word, Attributes attrs){
       int count=1;
        WordAttribute wa = new WordAttribute(word,attrs,count);
        int position = this.CheckPosition(word.word);
        boolean wordExists = false;

        if (position < this.words.size()) {
            wordExists = this.words.get(position).get(0).word.word.equals(word.word);
            for(int i = 0; i < this.words.get(position).get(0).attributecount.size(); i++)
            {
                   Document m = attrs.document;
                if(this.words.get(position).get(0).attributecount.get(i).attributes.document.compareTo(m) == 0)
               // if(this.words.get(position).get(0).attributecount.contains(this.words.get(position).get(0).attributecount.get(i).attributes.document))
                {
                    this.words.get(position).get(0).attributecount.get(i).count++;
                    //System.out.println( "count =" +  this.words.get(position).get(0).attributecount.get(i).getcount());
                }
                //System.out.println( "count =" +  this.words.get(position).get(0).attributecount.get(i).getcount());
            }
        }

        if (wordExists) {
            this.Existed(wa, position);
        }
        else {
            this.New(wa, position);
        }
    }

    private int CheckPosition (String word) {
        int lo = 0;
        int hi = this.words.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = word.compareTo(this.words.get(mid).get(0).word.word);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            }
            else return mid;
        }
        return lo;
    }

    private void Existed (WordAttribute wa, int index) {
        this.words.get(index).add(wa);
    }

    private void New (WordAttribute wa, int index) {
        ArrayList<WordAttribute> newList = new ArrayList();
        newList.add(wa);
        this.words.add(index, newList);
    }
}

