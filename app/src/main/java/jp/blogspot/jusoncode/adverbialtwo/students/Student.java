package jp.blogspot.jusoncode.adverbialtwo.students;


public class Student implements Comparable{

    public String name;
    public String comment = "\n\nComment: ";
    public String grammar = "\nGrammar: ";
    public String listening = "\nListening: ";
    public String vocabulary = "\nVocabulary: ";
    public String fluency = "\nFluency: ";
    public String communicative = "\nCommunicative Ability: ";


    public Student(String name){
        this.name = name;
    }

    @Override
    public int compareTo(Object another) {
        Student that = (Student)another;

        int thatLength = that.name.length();
        int thisLength = this.name.length();
        int loopCount = (thatLength > thisLength)? thisLength : thatLength;

        for(int count = 0; count <= loopCount; count++){
            if(this.name.charAt(count) > that.name.charAt(count)){
                return 1;
            }else if(this.name.charAt(count) < that.name.charAt(count)){
                return -1;
            }else if(this.name.charAt(count) == that.name.charAt(count)) {
                if(thatLength < thisLength){
                    return 1;
                }else{
                    return -1;
                }
            }
        }
        return 0;
    }

}
