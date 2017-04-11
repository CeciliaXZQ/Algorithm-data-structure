import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ceciliaX on 01/12/16.
 */
public class MySearchEngine implements TinySearchEngineBase, OrderableSearchEngine {
    private final Queries queryParser = new Queries(this);
    private final Indexing index = new Indexing();

    public void insert(Word word, Attributes attributes) {
        index.addWord(word, attributes);
    }

    public List<Document> search(String string) {
        return queryParser.execute(string);
    }

    public List<Document> search(String[] words, String orderBy, String direction) {
        List<Indexing.WordAttribute> Result = new ArrayList();
        List<Document> Mydoc = new ArrayList();
        List<Indexing.WordAttribute.AttributeCount> acList = new ArrayList();

        for (String query : words) {
            int biresult = this.BinarySearch(query);
            if (biresult != -1) {
                for (Indexing.WordAttribute wa : this.index.words.get(biresult)) {
                    Result.add(wa);
                    for (Indexing.WordAttribute.AttributeCount ac : wa.attributecount) {
                        acList.add(ac);
                    }
                }
            }
        }

        if (orderBy != null) {
            this.sortBy(orderBy, acList);
            if (direction.equals("desc")) {
                for (int i = acList.size() - 1; i >= 0; i--) {
                    if (!Mydoc.contains(acList.get(i).attributes.document)) {
                        Mydoc.add(acList.get(i).attributes.document);
                    }
                }
            } else {
                for (int i = 0; i < acList.size(); i++) {
                    if (!Mydoc.contains(acList.get(i).attributes.document)) {
                        Mydoc.add(acList.get(i).attributes.document);
                    }
                }
            }
        }
        for (int i = 0; i < acList.size(); i++) {
            if (!Mydoc.contains(acList.get(i).attributes.document)) {
                Mydoc.add(acList.get(i).attributes.document);
            }
        }
        return this.distinct(Mydoc);
    }

    private void sortBy (String by, List<Indexing.WordAttribute.AttributeCount> attricount) {
        if (by.equals("popularity")) {
            this.sortByPopularity(attricount);

        } else if (by.equals("occurrence")) {
            this.sortByOccurance(attricount);
        } else if (by.equals("count")) {
            this.sortByCount(attricount);
        }
    }

    private int BinarySearch(String word) {
        int lo = 0;
        int hi = this.index.words.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = word.compareTo(this.index.words.get(mid).get(0).word.word);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else return mid;
        }
        return -1;
    }

    private <T> List<T> distinct(List<T> list) {
        List<T> distinctList = new ArrayList<T>();
        for (T doc : list) {
            boolean found = false;
            for (T doc2 : distinctList) {
                if (doc2.equals(doc)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                distinctList.add(doc);
            }
        }
        return distinctList;
    }

    private void sortByPopularity(List<Indexing.WordAttribute.AttributeCount> attricount) {
        BubbleSort<Indexing.WordAttribute.AttributeCount> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
            //    @Override
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.attributes.document.popularity < o2.attributes.document.popularity) {
                    return -1;
                } else if (o1.attributes.document.popularity > o2.attributes.document.popularity) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        sorter.bubblesort(attricount);
    }
    private void sortByOccurance(List<Indexing.WordAttribute.AttributeCount> attricount) {
    BubbleSort<Indexing.WordAttribute.AttributeCount> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
        //         @Override
        public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
            if (o1.attributes.occurrence < o2.attributes.occurrence) {
                return -1;
            } else if (o1.attributes.occurrence > o2.attributes.occurrence) {
                return 1;
            } else {
                return 0;
            }
        }
    });
        sorter.bubblesort(attricount);

}

    private void sortByCount(List<Indexing.WordAttribute.AttributeCount> attricount) {
        BubbleSort<Indexing.WordAttribute.AttributeCount> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
            //         @Override
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.count < o2.count) {
                    return -1;
                } else if (o1.count > o2.count) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        sorter.bubblesort(attricount);
    }
}















 /*   private final Queries queryParser = new Queries(this);
   // private final Indexing index = new Indexing();

    public ArrayList<ArrayList<WordAttribute>> words = new ArrayList<ArrayList<WordAttribute>> ();

    class WordAttribute {
        public Word word;
        public List<WordAttribute.AttributeCount> attributecount = new ArrayList<WordAttribute.AttributeCount>();

        class AttributeCount {
            public Attributes attributes;
            public int count;

            public AttributeCount(Attributes attributes,int count) {
                this.attributes = attributes;
                this.count=count;
            }
            public int getcount()
            {
                return this.count;
            }
        }

        public WordAttribute (Word word,Attributes attributes,int count) {
            this.word = word;
            WordAttribute.AttributeCount ac = new WordAttribute.AttributeCount(  attributes,count) ;
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
                //
                // Document m = attrs.document;
               // if(this.words.get(position).get(0).attributecount.get(i).attributes.document.compareTo(m) == 0)
                 if(this.words.get(position).get(0).attributecount.contains(this.words.get(position).get(0).attributecount.get(i).attributes.document))
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

    public void insert(Word word, Attributes attributes) {
        addWord(word, attributes);
    }


    public List<Document> search(String string) {
        return queryParser.execute(string);
    }

    public List<Document> search(String[] words, String orderBy, String direction) {
        List<WordAttribute> Result = new ArrayList<WordAttribute>();
        List<Document> Mydoc = new ArrayList<Document>();
        List<WordAttribute.AttributeCount> acList = new ArrayList<WordAttribute.AttributeCount>();

        for (String query : words) {
            int biresult = this.BinarySearch(query);
            if (biresult != -1) {
                for (WordAttribute wa : this.words.get(biresult)) {
                    Result.add(wa);
                    for (WordAttribute.AttributeCount ac : wa.attributecount) {
                        acList.add(ac);
                    //    index.addWord(wa.word,ac.attributes);
                    }
                }
            }
        }


        if (orderBy != null) {
            this.sortBy(orderBy, acList);

            if (direction.equals("desc")) {
                for (int i = acList.size() - 1; i >= 0; i--) {
                    if (!Mydoc.contains(acList.get(i).attributes.document)) {
                        Mydoc.add(acList.get(i).attributes.document);
                    }
                }
            } else {
                for (int i = 0; i < acList.size(); i++) {
                    if (!Mydoc.contains(acList.get(i).attributes.document)) {
                        Mydoc.add(acList.get(i).attributes.document);
                    }
                }
            }
        }else {

            for (int i = 0; i < acList.size(); i++) {
                if (!Mydoc.contains(acList.get(i).attributes.document)) {
                    Mydoc.add(acList.get(i).attributes.document);
                }
            }
        }
        return this.distinct(Mydoc);
    }

    private void sortBy(String by, List<WordAttribute.AttributeCount> attricount) {
        if (by.equals("popularity")) {
        int condition =1;
            this.bubblesort(attricount,condition);

        } else if (by.equals("occurrence")) {
            int condition =2;
            this.bubblesort(attricount,condition);
           // this.sortByOccurance(attricount);

        } else if (by.equals("count")) {
            int condition =3;
            this.bubblesort(attricount,condition);
            // this.sortByCount(attricount);
        }
    }

    private int BinarySearch(String word) {
        int lo = 0;
        int hi = this.words.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = word.compareTo(this.words.get(mid).get(0).word.word);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else return mid;
        }
        return -1;
    }

    private <T> List<T> distinct(List<T> list) {
        List<T> distinctList = new ArrayList<T>();
        for (T doc : list) {
            boolean found = false;
            for (T doc2 : distinctList) {
                if (doc2.equals(doc)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                distinctList.add(doc);
            }
        }
        return distinctList;
    }

    private void bubblesort(List<WordAttribute.AttributeCount> list, int condition) {
        //  int shouldReverse = reverse ? -1 : 1;
        int r = list.size() - 2;
        boolean swapped = true;

        while (r >= 0 && swapped) {
            swapped = false;

            for (int i = 0; i <= r; i += 1) {
                //    int compareResult = this.compare(list.get(i), list.get(i + 1)) * shouldReverse;
                if ((list.get(i).attributes.occurrence > list.get(i).attributes.occurrence && condition == 2) || (list.get(i).attributes.document.popularity > list.get(i).attributes.document.popularity && condition == 1) || (list.get(i).count > list.get(i).count && condition == 3)) {
                    // if (compareResult > 0) {
                    WordAttribute.AttributeCount temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    swapped = true;
                }
            }
            r -= 1;
         //   System.out.println(" inside loop");
        }
    }
}

/*
    private void sortByPopularity(List<Indexing.WordAttribute.AttributeCount> attricount) {
        BubbleSort<Indexing.WordAttribute.AttributeCount> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
            //    @Override
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.attributes.document.popularity < o2.attributes.document.popularity) {
                    return -1;
                } else if (o1.attributes.document.popularity > o2.attributes.document.popularity) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        sorter.bubblesort(attricount);
    }


    private void sortByOccurance(List<Indexing.WordAttribute.AttributeCount> attricount) {
        BubbleSort<Indexing.WordAttribute.AttributeCount> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.attributes.occurrence < o2.attributes.occurrence) {
                    return -1;
                } else if (o1.attributes.occurrence > o2.attributes.occurrence) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        sorter.bubblesort(attricount);

    }

    private void sortByCount(List<Indexing.WordAttribute.AttributeCount> attricount) {
        BubbleSort<Indexing.WordAttribute.AttributeCount> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
            //         @Override
            //    public int countplus(Indexing.WordAttribute o, )
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.count < o2.count) {
                    return -1;
                } else if (o1.count > o2.count) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
        System.out.println(" inside loop");
        sorter.bubblesort(attricount);
    }
}












      /*
        if (orderBy != null) {
            //  boolean reverse = false;
            if (orderBy.equals("popularity")) {
                // this.sortByPopularity(wa, reverse);
                condition = 1;
                for (int i = 0; i < words.length; i++) {
                    int index =this.BinarySearch(words[i]);
                    acList= documentfile(acList,index);
                }
                bubblesort(acList,condition);

            } else if (orderBy.equals("occurrence")) {
                // this.sortByOccurance(wa, reverse);
                condition = 2;
                for (int i = 0; i < words.length; i++) {
                    int index =this.BinarySearch(words[i]);
                    acList= documentfile(acList,index);
                }
                bubblesort(acList, condition);
            }
            bubblesort(acList, condition);

            } else if (orderBy.equals("count")) {
                //    this.sortByCount(wa, reverse);
                condition = 3;
                for (int i = 0; i < words.length; i++) {
                    int index =this.BinarySearch(words[i]);
                    acList= documentfile(acList,index);
                }
                bubblesort(acList, condition);
            }
            bubblesort(acList, condition);

        //this.sortBy(orderBy, Result, reverse,condition);}

        if (direction.equals("desc")) {
            for (int i = acList.size() - 1; i >= 0; i--) {
                if (!Mydoc.contains(acList.get(i).attributes.document)) {
                    Mydoc.add(acList.get(i).attributes.document);
                }
            }
        } else {
            for (int i = 0; i < acList.size(); i++) {
                if (!Mydoc.contains(acList.get(i).attributes.document)) {
                    Mydoc.add(acList.get(i).attributes.document);
                }
            }
        }

        return Mydoc;




        return this.distinct(Mydoc);

    /*   // for (index.words.get() : Result) {
        for (Indexing.WordAttribute wa : Result) {
            for (int i=0; i < wa.attributecount,i++){
           //for (Indexing.WordAttribute.AttributeCount ac : Result.get(wa).attributecount) {
                Mydoc.add(wa.attributecount.get(i).attributes.document);
            }
        }



    public List<Indexing.WordAttribute.AttributeCount> documentfile(List<Indexing.WordAttribute.AttributeCount> acList, int index) {
        Indexing.WordAttribute wa = Indexing.words.get(index).get(0);

        for (int i = 0; i < Indexing.words.get(index).get(0).attributecount.size(); i++) {
            if (!acList.contains(Indexing.words.get(index).get(0).attributecount.get(i))) {
                acList.add(wa.attributecount.get(i));
            }
        }
        return acList;

    }




    private int BinarySearch(String word) {

        int lo = 0;
        int hi = this.index.words.size() - 1;
        //  if (hi <= lo) return lo;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = word.compareTo(this.index.words.get(mid).get(0).word.word);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else return mid;
        }
        return -1;
    }

    private <T> List<T> distinct(List<T> list) {
        List<T> distinctList = new ArrayList<T>();

        for (T doc : list) {
            boolean found = false;
            for (T doc2 : distinctList) {
                if (doc2.equals(doc)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                distinctList.add(doc);
            }
        }

        return distinctList;
    }

    private void bubblesort(List<Indexing.WordAttribute.AttributeCount> list, int condition) {
        //  int shouldReverse = reverse ? -1 : 1;
        int r = list.size() - 2;
        boolean swapped = true;

        while (r >= 0 && swapped) {
            swapped = false;

            for (int i = 0; i <= r; i += 1) {
                //    int compareResult = this.compare(list.get(i), list.get(i + 1)) * shouldReverse;
                if ((list.get(i).attributes.occurrence > list.get(i).attributes.occurrence && condition == 2) ||
                        (list.get(i).attributes.document.popularity > list.get(i).attributes.document.popularity && condition == 1)
                        || (list.get(i).count > list.get(i).count && condition == 3)) {
                    // if (compareResult > 0) {
                    Indexing.WordAttribute.AttributeCount temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    swapped = true;
                }
            }
            r -= 1;
        }

    }
}
 /*   private void bubblesort(List<Indexing.WordAttribute.AttributeCount> list, int condition) {
        //  int shouldReverse = reverse ? -1 : 1;
        int r = list.size() - 2;
        boolean swapped = true;

        while (r >= 0 && swapped) {
            swapped = false;

            for (int i = 0; i <= r; i += 1) {
                //    int compareResult = this.compare(list.get(i), list.get(i + 1)) * shouldReverse;
                if ((list.get(i).attributes.occurrence > list.get(i).attributes.occurrence && condition == 2) ||
                        (list.get(i).attributes.document.popularity > list.get(i).attributes.document.popularity && condition == 1)
                        || (list.get(i).count > list.get(i).count && condition == 3)) {
                    // if (compareResult > 0) {
                    Indexing.WordAttribute.AttributeCount temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    swapped = true;
                }
            }
            r -= 1;
        }
    }*/





 /*   private void sortByPopularity (List<Indexing.WordAttribute> wa, boolean reverse) {
   /*     BubbleSort<Indexing.WordAttribute> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
        //    @Override
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.attributes.document.popularity < o2.attributes.document.popularity) { return -1; }
                else if (o1.attributes.document.popularity > o2.attributes.document.popularity) { return 1; }
                else { return 0; }
            }

        bubblesort(wa, reverse);

    }

    private void sortByOccurance (List<Indexing.WordAttribute> attricount, boolean reverse) {
        BubbleSort<Indexing.WordAttribute> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
   //         @Override
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.attributes.occurrence < o2.attributes.occurrence) {
                    return -1;
                } else if (o1.attributes.occurrence > o2.attributes.occurrence) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        sorter.bubblesort(attricount, reverse);

    }
    private void sortByCount (List<Indexing.WordAttribute> attricount, boolean reverse) {
       // int freq = Collections.frequency(wordAttributes, );
        BubbleSort<Indexing.WordAttribute> sorter = new BubbleSort(new Comparator<Indexing.WordAttribute.AttributeCount>() {
            //         @Override
        //    public int countplus(Indexing.WordAttribute o, )
            public int compare(Indexing.WordAttribute.AttributeCount o1, Indexing.WordAttribute.AttributeCount o2) {
                if (o1.count < o2.count) {
                    return -1;
                } else if (o1.count > o2.count) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        sorter.bubblesort(attricount, reverse);
       // System.out.println(count);
    }*/






 /*    if (orderBy != null) {
            //  boolean reverse = false;
            if (orderBy.equals("popularity")) {
                // this.sortByPopularity(wa, reverse);
                condition = 1;
                for (int i = 0; i < words.length; i++) {
                    //   int index = this.BinarySearch(words[i]);
                    // acList = documentfile(acList, index);
                }
                bubblesort(acList, condition);

            } else if (orderBy.equals("occurrence")) {
                // this.sortByOccurance(wa, reverse);
                condition = 2;
                for (int i = 0; i < words.length; i++) {
                    //     int index = this.BinarySearch(words[i]);
                    //   acList = documentfile(acList, index);
                }
                bubblesort(acList, condition);

            } else if (orderBy.equals("count")) {
                //    this.sortByCount(wa, reverse);
                condition = 3;
                for (int i = 0; i < words.length; i++) {
                    //   int index = this.BinarySearch(words[i]);
                    //  acList = documentfile(acList, index);
                }
                bubblesort(acList, condition);
            }
        }
        for (Indexing.WordAttribute wa : Result) {
            for (int i = 0; i < wa.attributecount.size();i++){
                //for (Indexing.WordAttribute.AttributeCount ac : Result.get(wa).attributecount) {
                Mydoc.add(wa.attributecount.get(i).attributes.document);
            }
        }
        return this.distinct(Mydoc);

        if (direction.equals("desc")) {
            for (int i = acList.size() - 1; i >= 0; i--) {
                if (!Mydoc.contains(acList.get(i).attributes.document)) {
                    Mydoc.add(acList.get(i).attributes.document);
                }
            }
        } else {
            for (int i = 0; i < acList.size(); i++) {
                if (!Mydoc.contains(acList.get(i).attributes.document)) {
                    Mydoc.add(acList.get(i).attributes.document);
                }
            }
        }
    }*/