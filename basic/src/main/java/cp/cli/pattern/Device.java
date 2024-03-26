package cp.cli.pattern;

public class Device {
    private String name;

    public Device(String name) {
        this.name = name;
    }

    public void on() {
        System.out.println(name + " 设备打开");
    }

    public void off() {
        System.out.println(name + " 设备关闭");
    }
}