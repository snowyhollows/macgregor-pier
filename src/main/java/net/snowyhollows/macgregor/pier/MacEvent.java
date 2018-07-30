package net.snowyhollows.macgregor.pier;

public class MacEvent {
    public final MacEventType type;
    public final String source;
    public final String value;

    public MacEvent(MacEventType type, String source, String value) {
        this.type = type;
        this.source = source;
        this.value = value;
    }
}
