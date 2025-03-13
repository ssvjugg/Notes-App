package ru.usernamedrew;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Note implements Serializable, Comparable<Note> {
    private String title;
    private String content;

    @Override
    public int compareTo(Note o) {
        return Integer.compare(content.length(), o.content.length());
    }
}
