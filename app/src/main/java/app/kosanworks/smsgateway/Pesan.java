package app.kosanworks.smsgateway;

/**
 * Created by ghost on 05/06/17.
 */

public class Pesan {
    String id;
    String from;
    String message;
    String date;

    public Pesan() {
    }

    public Pesan(String id, String from, String message, String date) {
        this.id = id;
        this.from = from;
        this.message = message;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
