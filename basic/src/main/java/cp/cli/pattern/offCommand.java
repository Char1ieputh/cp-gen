package cp.cli.pattern;

public class offCommand implements Command {
    private Device device;

    public offCommand(Device device) {
        this.device = device;
    }

    public void execute() {
        device.off();
    }
}