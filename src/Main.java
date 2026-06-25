
import Back_End.Edge;
import Back_End.Network;
import Front_End.NetworkFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Network network = new Network();

            NetworkFrame frame =
                    new NetworkFrame(network);

            frame.setVisible(true);

        });
    }
}