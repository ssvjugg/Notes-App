package ru.usernamedrew;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

enum Command {
    Add, Edit, Delete, Get, Exit;
}

@Slf4j
public final class NotesManager {
    private HashMap<Integer, Note> notes;
    private int id = 0;
    private static final String FILE_NAME = "notes.dat";

    public NotesManager() {
        notes = loadNotes();
        if (notes.isEmpty()) {
            notes.put(id++ ,new Note("Title", "Hello, this is my note!"));
        }
    }

    /**
     * The method in which the action is selected
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Command cmd;

        do {
            System.out.print("Enter command (Add, Edit, Delete, Get, Exit): ");
            cmd = Command.valueOf(scanner.nextLine().toUpperCase());
            switch (cmd) {
                case Add -> addNote(scanner);
                case Edit -> editNote(scanner);
                case Delete -> deleteNote(scanner);
                case Get -> printNotes();
                case Exit -> saveNotes();
                default -> {
                    System.out.println("Invalid command");
                    log.warn("Invalid command");
                }
            }
        } while (cmd != Command.Exit);
    }

    /**
     * Method that implements adding the note
     * @param scanner input stream
     */
    private void addNote(Scanner scanner) {
        System.out.print("Enter the title of the note: ");
        String title = scanner.nextLine();
        System.out.print("Enter the content of the note: ");
        String content = scanner.nextLine();
        notes.put(id++, new Note(title, content));
        log.info("Note added: " + id--);
    }

    /**
     * Method that implements deleting the note
     * @param scanner input stream
     */
    private void deleteNote(Scanner scanner) {
        System.out.print("Enter the number of the note: ");
        int noteNumber = Integer.parseInt(scanner.nextLine());
        notes.remove(noteNumber);
        log.info("Note deleted: " + noteNumber);
    }

    /**
     * Method that implements editing the note
     * @param scanner input stream
     */
    private void editNote(Scanner scanner) {
        System.out.print("Enter the number of the note: ");
        int noteNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the new content of the note: ");
        String content = scanner.nextLine();
        notes.get(noteNumber).setContent(content);
        log.info("Note updated: " + noteNumber);
    }

    /**
     * Function that saves all provided notes
     */
    private void saveNotes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(notes);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * A function that implements the unloading of all records from the memory
     * @return HashMap of identifications and notes
     */
    @SuppressWarnings("unchecked")
    private HashMap<Integer, Note> loadNotes() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (HashMap<Integer, Note>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
            return new HashMap<>();
        }
    }


    private Note findNoteByTitle(String title) {
        return null;
    }

    private String printNotes() {
        return null;
    }
}
