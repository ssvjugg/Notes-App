package ru.usernamedrew;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

enum Command {
    ADD, EDIT, DELETE, GET, EXIT;
}

@Slf4j
@Getter
public final class NotesManager {
    private final HashMap<Integer, Note> notes;
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
        Command cmd = null;

        do {
            System.out.print("Enter command (Add, Edit, Delete, Get, Exit): ");
            String input = scanner.nextLine();
            try {
                cmd = Command.valueOf(input.toUpperCase());
                switch (cmd) {
                    case ADD -> addNote(scanner);
                    case EDIT -> editNote(scanner);
                    case DELETE -> deleteNote(scanner);
                    case GET -> printNotes();
                    case EXIT -> saveNotes();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command. Please try again.");
                log.warn("Invalid command: " + input);
            }
        } while (cmd != Command.EXIT);
    }

    /**
     * Method that implements adding the note
     * @param scanner input stream
     */
    public void addNote(Scanner scanner) {
        System.out.print("Enter the title of the note: ");
        String title = scanner.nextLine();
        String content = readContent(scanner);
        while (notes.containsKey(id)) id++; // Not so efficient(
        notes.put(id, new Note(title, content));
        log.info("Note added: " + id);
        id++;
    }

    /**
     * Method that implements deleting the note
     * @param scanner input stream
     */
    public void deleteNote(Scanner scanner) {
        System.out.print("Enter the number of the note: ");
        int noteNumber = Integer.parseInt(scanner.nextLine());
        notes.remove(noteNumber);
        log.info("Note deleted: " + noteNumber);
    }

    /**
     * Method that implements editing the note
     * @param scanner input stream
     */
    public void editNote(Scanner scanner) {
        System.out.print("Enter the number of the note: ");
        int noteNumber = Integer.parseInt(scanner.nextLine());
        String content = readContent(scanner);
        notes.get(noteNumber).setContent(content);
        log.info("Note updated: " + noteNumber);
    }

    /**
     * Auxiliary function for reading from a standard input stream, line by line
     * @param scanner input stream
     * @return String with text from standard input
     */
    private String readContent(Scanner scanner) {
        System.out.println("Enter the content of the note (type 'END' on a new line to finish):");
        StringBuilder contentBuilder = new StringBuilder();
        String line;

        while (!(line = scanner.nextLine()).equals("END")) {
            contentBuilder.append(line).append("\n");
        }

        return contentBuilder.toString().trim();
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
        return notes.values().stream().filter(note -> note.getTitle().equals(title)).findFirst().orElse(null);
    }

    public void deleteAllNotes() {
        notes.clear();
    }

    private void printNotes() {
        StringBuilder sb = new StringBuilder();
        for (var entry : notes.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n\n");
        }
        if (sb.length() >= 2)
            sb.setLength(sb.length() - 2);
        System.out.println(sb.toString());
    }
}
