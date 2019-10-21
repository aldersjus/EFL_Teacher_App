package jp.blogspot.jusoncode.adverbialtwo.lessons;


public class Lesson implements Comparable{

    public String name;
    public String pupils = "\n\nStudents: ";
    public String takenLessons = "\n\nLessons Taken: ";

    public Lesson(String name){
        this.name = name;
    }


    @Override
    public int compareTo(Object another) {
        Lesson that = (Lesson)another;

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
