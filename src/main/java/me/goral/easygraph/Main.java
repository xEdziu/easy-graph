package me.goral.easygraph;

public class Main {
    public static void main(String[] args) {

        String version = "1.0-SNAPSHOT";

        String usagePrint = "Usage: java -jar easygraph-" + version + ".jar [options]";

        if (args.length > 0) {
            if (args[0].equals("--no-gui")) {
                System.out.println("Running in no-gui mode");
            } else if (args[0].equals("--help")) {
                System.out.println(usagePrint);
                System.out.println("Options:");
                System.out.println("  --no-gui  Run in no-gui mode");
                System.out.println("  --help    Print this help message");
            }
        } else {
            HelloApplication.main(args);
        }
    }
}
