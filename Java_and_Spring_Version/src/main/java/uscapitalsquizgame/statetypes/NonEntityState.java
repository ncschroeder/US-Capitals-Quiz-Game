package uscapitalsquizgame.statetypes;

import java.util.Arrays;
import java.util.List;

public class NonEntityState {
    private final String name;
    private final String capital;
    private final List<String> cities;
    
    public NonEntityState(EntityState entityState) {
        this.name = entityState.getName();
        this.capital = entityState.getCapital();
        String[] citiesArr = entityState.getCities().split(",");
        this.cities = List.copyOf(Arrays.asList(citiesArr));
    }
    
    @Override
    public String toString() {
        return String.format("NonEntityState[name=%s, capital=%s, cities=%s]", name, capital, cities);
    }
    
    @Override
    public boolean equals(Object obj) {
        return
            obj instanceof NonEntityState state && this.name.equals(state.name) &&
            this.capital.equals(state.capital) && this.cities.equals(state.cities);
    }
    
    public String getName() {
        return name;
    }
    
    public String getCapital() {
        return capital;
    }
    
    public List<String> getCities() {
        return cities;
    }
}
