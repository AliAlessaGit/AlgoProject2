package Front_End;

import Back_End.Network;

public class MenuController {

    private NetworkFrame frame;

    private Network network;

    public MenuController(
            NetworkFrame frame,
            Network network) {

        this.frame = frame;
        this.network = network;
    }
}