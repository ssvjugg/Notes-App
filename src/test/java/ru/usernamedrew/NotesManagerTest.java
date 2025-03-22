package ru.usernamedrew;

import org.junit.jupiter.api.*;

import java.util.Scanner;

import static org.mockito.Mockito.*;


public class NotesManagerTest {
    private NotesManager notesManager;
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() {
        notesManager = new NotesManager();
        mockScanner = mock(Scanner.class);
    }

    @AfterEach
    public void tearDown() {
        notesManager.deleteAllNotes();
    }

    @Test
    public void testDefaultNote() {
        Assertions.assertEquals(1, notesManager.getNotes().size());
    }

    @Test
    public void testAddNote() {
        when(mockScanner.nextLine()).thenReturn("Test title", "Test content", "END");
        notesManager.addNote(mockScanner);
        Note addedNote = notesManager.getNotes().get(1);
        Assertions.assertEquals("Test title", addedNote.getTitle());
        Assertions.assertEquals("Test content", addedNote.getContent());
    }

    @Test
    public void testDeleteNote() {
        when(mockScanner.nextLine()).thenReturn("0");
        notesManager.deleteNote(mockScanner);
        Assertions.assertEquals(0, notesManager.getNotes().size());
    }

    @Test
    public void testEditNote() {
        when(mockScanner.nextLine()).thenReturn("0", "Test content", "END");
        notesManager.editNote(mockScanner);
        Note editedNote = notesManager.getNotes().get(0);
        Assertions.assertEquals("Test content", editedNote.getContent());
    }
}
