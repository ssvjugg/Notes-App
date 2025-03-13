package ru.usernamedrew;


public class App {
    public static void main( String[] args ) {
        Note note = new Note("Title", "Hello, this is my note!");
        System.out.println(note.getTitle());
    }
}
