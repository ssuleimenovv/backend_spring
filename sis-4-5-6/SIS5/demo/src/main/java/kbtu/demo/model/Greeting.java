package kbtu.demo.model;

public class Greeting {
    private String msg;
    private String name;

    public Greeting() {}
    public Greeting(String msg, String name) {
        this.msg = msg;
        this.name = name;
    }

    public String getMsg() { return msg; }
    public String getName() { return name; }

    public void setMsg(String msg) { this.msg = msg; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Greeting{" + "msg='" + msg + '\'' + ", name='" + name + '\'' + '}';
    }
}
// getters, setters, toString(0 if your message not string types, constructors