package kbtu.demo.model;

public class Farewell {
    private String message;
    private Integer remainingMinutes;

    public Farewell() {}
    public Farewell(String message, Integer remainingMinutes) {
        this.message = message;
        this.remainingMinutes = remainingMinutes;
    }

    public String getMessage() { return message; }
    public Integer getRemainingMinutes() { return remainingMinutes; }

    public void setMessage(String message) { this.message = message; }
    public void setRemainingMinutes(Integer remainingMinutes) { this.remainingMinutes = remainingMinutes; }

    @Override
    public String toString() {
        return "Farewell{" + "message='" + message + '\'' + ", remainingMinutes=" + remainingMinutes + '}';
    }
}
// Farewell, Greeting needed for serializing/deserializing objects to JSON on sending/receiving
