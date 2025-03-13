package ru.usernamedrew;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Scanner;

public class AppTest {
    private NotesManager notesManager;

    @BeforeEach
    public void setUp() {
        notesManager = new NotesManager();
    }

    @Test
    public void testDefaultNote() {
        Assertions.assertEquals(1, notesManager.getNotes().size());
    }
}
