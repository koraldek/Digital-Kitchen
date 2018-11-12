package pl.krasnowski.DigitalKitchen.model.domain.social;

public enum Folder {
    UNREAD("unread"),
    BIN("bin"),
    READ("read"),
    ANNOUNCMENT("announcement");

    private final String name;

    Folder(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
