package jp.blogspot.jusoncode.adverbialtwo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import jp.blogspot.jusoncode.adverbialtwo.lessons.Lesson;
import jp.blogspot.jusoncode.adverbialtwo.students.Student;

public class DataBaseDAO  {

    private final String STUDENT_TABLE = "student";
    private final String STUDENT_NAME = "name";
    private final String STUDENT_COMMENT = "comment";
    private final String STUDENT_GRAMMAR = "grammar";
    private final String STUDENT_VOCABULARY = "vocabulary";
    private final String STUDENT_LISTENING = "listening";
    private final String STUDENT_FLUENCY = "fluency";
    private final String STUDENT_COMMUNICATIVE_ABILITY = "communicative";
    private final String LESSON_TABLE = "lesson";
    private final String LESSON_NAME = "name";
    private final String LESSON_PUPILS = "pupils";//
    private final String LESSON_TAKEN = "taken";
    private DataBase database;
    private SQLiteDatabase db;
    public final ArrayList<String> studentInMap = new ArrayList<>();
    public final HashMap<String, Student> studentMap = new HashMap<>();
    public final ArrayList<String> lessonInMap = new ArrayList<>();
    public final HashMap<String, Lesson> lessonMap = new HashMap<>();


    public DataBaseDAO(Context context){
        this.database = new DataBase(context);
        readStudentIntoHash();
        readLessonIntoHash();

    }


    /////new method for improved database
    public void addNewStudent(Student s, View view){

        Student student = studentMap.get(s.name);

        db = database.getWritableDatabase();

        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            //Check this finds ok or pass in name as a string...
            values.put(STUDENT_NAME, student.name);
            values.put(STUDENT_COMMENT, student.comment);
            values.put(STUDENT_GRAMMAR,student.grammar);
            values.put(STUDENT_VOCABULARY,student.vocabulary);
            values.put(STUDENT_LISTENING,student.listening);
            values.put(STUDENT_FLUENCY, student.fluency);
            values.put(STUDENT_COMMUNICATIVE_ABILITY, student.communicative);

            db.insertOrThrow(STUDENT_TABLE, null, values);

        }catch(Exception e){
            Toast.makeText(view.getContext(),"Error adding new student, report to developer.",Toast.LENGTH_SHORT).show();
        }finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    ////update method....test
    public void updateStudentRow(Student student,View view){

        db = database.getWritableDatabase();

        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();
            //Check this finds ok or pass in name as a string...
            values.put(STUDENT_NAME, student.name);
            values.put(STUDENT_COMMENT, student.comment);
            values.put(STUDENT_GRAMMAR,student.grammar);
            values.put(STUDENT_VOCABULARY,student.vocabulary);
            values.put(STUDENT_LISTENING,student.listening);
            values.put(STUDENT_FLUENCY, student.fluency);
            values.put(STUDENT_COMMUNICATIVE_ABILITY, student.communicative);

            db.update(STUDENT_TABLE,values,STUDENT_NAME + "= ?",new String[]{student.name});

            db.setTransactionSuccessful();

        }catch(Exception e){
            Toast.makeText(view.getContext(),"Error updating student, report to developer.",Toast.LENGTH_SHORT).show();
        }finally{
            db.endTransaction();
        }

    }

    ///NEW READ DATA
    private void readStudentIntoHash() {
        String grammarHere;
        String vocabHere;
        String listenHere;
        String fluencyHere;
        String communiHere;
        String name;
        String comment;

        studentInMap.clear();

        db = database.getReadableDatabase();
        db.beginTransaction();

        String columns[] = {STUDENT_NAME,STUDENT_COMMENT,STUDENT_GRAMMAR,STUDENT_VOCABULARY,STUDENT_LISTENING,STUDENT_FLUENCY,STUDENT_COMMUNICATIVE_ABILITY};
        Cursor cursor = db.query(STUDENT_TABLE, columns, null, null, null, null, STUDENT_NAME);

        while (cursor.moveToNext()) {

            name = cursor.getString(0);
            comment = cursor.getString(1);
            grammarHere = cursor.getString(2);
            vocabHere = cursor.getString(3);
            listenHere = cursor.getString(4);
            fluencyHere = cursor.getString(5);
            communiHere = cursor.getString(6);
            Student student = new Student(name);
            student.comment = comment;
            student.grammar = grammarHere;
            student.vocabulary = vocabHere;
            student.listening = listenHere;
            student.fluency = fluencyHere;
            student.communicative = communiHere;
            studentMap.put(name,student);
            if(!studentInMap.contains(name)){
                studentInMap.add(name);
            }

        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    //Delete student...
    public void deleteStudent(String nameIn,View view){
        db = database.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(STUDENT_TABLE, STUDENT_NAME + "= ?", new String[]{nameIn});
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Toast.makeText(view.getContext(),"Error deleting student, report to developer.",Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    /////new method for improved database
    public void addNewLesson(Lesson l,View view){

        Lesson lesson = lessonMap.get(l.name);

        db = database.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(LESSON_NAME, lesson.name);
            values.put(LESSON_PUPILS, lesson.pupils);
            values.put(LESSON_TAKEN, lesson.takenLessons);
            db.insertOrThrow(LESSON_TABLE, null, values);

        }catch(Exception e){
            Toast.makeText(view.getContext(),"Error adding new lesson, report to developer.",Toast.LENGTH_SHORT).show();
        }finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }


    ////update method....test
    public void updateLessonRow(Lesson lesson,View view){

        db = database.getWritableDatabase();

        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();

            values.put(LESSON_NAME, lesson.name);
            values.put(LESSON_PUPILS, lesson.pupils);
            values.put(LESSON_TAKEN, lesson.takenLessons);

            db.update(LESSON_TABLE,values,LESSON_NAME + "= ?",new String[]{lesson.name});

            db.setTransactionSuccessful();

        }catch(Exception e){
            Toast.makeText(view.getContext(),"Error updating lesson, report to developer.",Toast.LENGTH_SHORT).show();
        }finally{
            db.endTransaction();
        }

    }

    //Lesson
    private void readLessonIntoHash(){
        String name;
        String pupils;
        String lessons;

        lessonInMap.clear();

        db = database.getReadableDatabase();
        String columns[] = {LESSON_NAME,LESSON_PUPILS,LESSON_TAKEN};

        db.beginTransaction();
        ///Cursor finds names..
        Cursor cursor = db.query(LESSON_TABLE,columns,null,null,null,null,LESSON_NAME);

        //Loop through database add names to array list...
        while(cursor.moveToNext()){
            name = cursor.getString(0);
            pupils = cursor.getString(1);
            lessons = cursor.getString(2);
            Lesson lesson = new Lesson(name);
            lesson.pupils = pupils;
            lesson.takenLessons = lessons;
            lessonMap.put(name,lesson);
            if(!lessonInMap.contains(name)){
                lessonInMap.add(name);
            }

        }

        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    //Delete lesson...
    public void deleteLesson(String nameIn,View view){

        db = database.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(LESSON_TABLE, LESSON_NAME  + "= ?", new String[]{nameIn});
            db.setTransactionSuccessful();
            db.endTransaction();

            } catch (Exception e) {
                Toast.makeText(view.getContext(),"Error deleting lesson, report to developer.",Toast.LENGTH_SHORT).show();
            }
        db.close();

    }

    ///Close the database when finished using it...
    public void closeDatabase(){
        db.close();
    }

}

