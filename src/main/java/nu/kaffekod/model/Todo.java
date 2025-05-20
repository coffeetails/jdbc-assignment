package nu.kaffekod.model;

import java.time.LocalDate;

public class Todo {
    private int id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Person assignee;


    public Todo(String title, String description, LocalDate deadline, Person assignee) {
        this(title, assignee);
        setDescription(description);
        setDeadline(deadline);
    }
    public Todo(String title, Person assignee) {
        this(title);
        setAssignee(assignee);
    }
    public Todo(String title) {
        setTitle(title);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", assignee=" + assignee +
                '}';
    }
}
