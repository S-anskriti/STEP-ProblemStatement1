import java.util.*;

public class ProblemStatement1 {

    static class Spot {
        String plate;
        long entry;
    }

    static Spot[] table = new Spot[500];
    static int occupied = 0;

    public static void main(String[] args) {

        parkVehicle("ABC1234");
        parkVehicle("ABC1235");
        parkVehicle("XYZ9999");

        exitVehicle("ABC1234");

        getStatistics();
    }

    static int hash(String plate) {
        return Math.abs(plate.hashCode()) % table.length;
    }

    static void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % table.length;
            probes++;
        }

        Spot s = new Spot();
        s.plate = plate;
        s.entry = System.currentTimeMillis();

        table[index] = s;
        occupied++;

        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }

    static void exitVehicle(String plate) {

        for (int i = 0; i < table.length; i++) {

            if (table[i] != null && table[i].plate.equals(plate)) {

                long duration = System.currentTimeMillis() - table[i].entry;
                double hours = duration / 3600000.0;
                double fee = hours * 5;

                table[i] = null;
                occupied--;

                System.out.println("Spot #" + i + " freed, Duration: " + hours + "h, Fee: $" + fee);
                return;
            }
        }
    }

    static void getStatistics() {

        double occ = (occupied * 100.0) / table.length;
        System.out.println("Occupancy: " + occ + "%");
    }
}