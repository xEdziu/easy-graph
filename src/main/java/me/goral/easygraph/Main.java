package me.goral.easygraph;

import me.goral.easygraph.console.GraphDemo;

public class Main {
    public static void main(String[] args) {

        String version = "1.0-SNAPSHOT";

        String usagePrint = "Usage: java -jar easygraph-" + version + ".jar [options]";

        if (args.length > 0) {
            if (args[0].equals("--tests")) {
                System.out.println("Preparing tests. Please wait for the results.");
                GraphDemo.main(args);
            } else if (args[0].equals("--help")) {
                System.out.println(usagePrint);
                System.out.println("Options:");
                System.out.println("  --tests  Run tests for the graphs and algorithms");
                System.out.println("  --help    Print this help message");
            }
        } else {
            HelloApplication.main(args);
        }
    }
}
