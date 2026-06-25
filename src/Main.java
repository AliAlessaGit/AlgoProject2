import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Network network = new Network();

        network.put("Damascus", new ArrayList<>(Arrays.asList(
                new Edge("Homs", 120))));

        network.put("Homs", new ArrayList<>(Arrays.asList(
                new Edge("Hama", 70),
                new Edge("Tartus", 90))));

        network.put("Hama", new ArrayList<>(Arrays.asList(
                new Edge("Aleppo", 80))));

        network.put("Tartus", new ArrayList<>(Arrays.asList(
                new Edge("Latakia", 50))));

        network.put("Aleppo", new ArrayList<>(Arrays.asList(
                // new Edge("Damascus", 50),
                new Edge("Idlib", 60))));

        network.put("Idlib", new ArrayList<>(Arrays.asList(
                new Edge("Latakia", 40))));

        network.put("Latakia", new ArrayList<>());

        network.put("Ar-Raqqah", new ArrayList<>());

        network.readAndPrintInFile();


        // System.out.println(network.containsCycle());

        // for (Station station : network.sort()) {
        //         System.out.print(station.name + ", ");
        // }


        // Scanner input = new Scanner(System.in);

        // int choice;

        // while (true) {

        //         System.out.println("\n===== Train Network =====");
        //         System.out.println("1. Add Station");
        //         System.out.println("2. Add Route");
        //         System.out.println("3. Import network");
        //         System.out.println("4. Export network");
        //         System.out.println("5. Print network");
        //         System.out.println("6. Exit");
        //         System.out.print("Choice: ");

        //         choice = input.nextInt();
        //         input.nextLine(); // تنظيف الـ buffer

        //         switch (choice) {

        //                 case 1:
        //                         System.out.print("Station Name: ");
        //                         String station = input.nextLine();

        //                         network.addStation(new Station(station));

        //                         System.out.println("Station Added.");
        //                         break;

        //                 case 2:

        //                         Station source = network.chooseStation(input);

        //                         Station destination = network.chooseStation(input);

        //                         System.out.print("Distance: ");
        //                         int distance = input.nextInt();

        //                         network.addRoute(source.name, destination.name, distance);

        //                         break;

        //                 case 3:

        //                         network.loadSampleData();

        //                         System.out.println("network Imported.");
        //                         break;

        //                 case 4:

        //                         System.out.println("\nNetwork:");

        //                         System.out.println(network.exportNetwork());

        //                         break;
        //                 case 5:
        //                         network.printNetwork();
        //                         break;
        //                 case 6:

        //                         System.out.println("Good Bye.");
        //                         input.close();
        //                         return;

        //                 default:

        //                         System.out.println("Invalid Choice.");
        //         }
        // }
    }
}