package com.example.todolist.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "todo_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    @ManyToOne(fetch = FetchType.LAZY)
    private ToDoList toDoList;
}
