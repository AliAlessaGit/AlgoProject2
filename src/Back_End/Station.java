package Back_End;

import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class Station {
    static Map<String, String> symbols = Map.ofEntries(
            entry("Damascus", "DM"),
            entry("Aleppo", "AL"),
            entry("Homs", "HS"),
            entry("Hama", "HM"),
            entry("Lattakia", "LA"),
            entry("Tartous", "TA"),
            entry("Idlib", "ID"),
            entry("Daraa", "DR"),
            entry("As-Suwayda", "SU"),
            entry("Quneitra", "QU"),
            entry("Deir ez-Zor", "DY"),
            entry("Al-Hasakah", "HK"),
            entry("Ar-Raqqah", "RA"),
            entry("Rif Dimashq", "RD"));

    public String name;
    public String symbol;

    public Station(String name) {
        this.name = name;
        this.symbol = symbols.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Station))
            return false;

        Station station = (Station) o;

        return Objects.equals(name, station.name)
                && Objects.equals(symbol, station.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, symbol);
    }
}