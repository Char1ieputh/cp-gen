package cp.cli.pattern;

public class onCommand implements Command {
    private Device device;

    public onCommand(Device device) {
        this.device = device;
    }

    public void execute() {
        device.on();
    }
}