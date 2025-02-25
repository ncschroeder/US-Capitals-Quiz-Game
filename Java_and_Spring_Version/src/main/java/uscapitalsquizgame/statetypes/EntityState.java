package uscapitalsquizgame.statetypes;

import jakarta.persistence.*;

@Entity
public class EntityState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(unique = true, length = 30)
    private String name;
    
    @Column(length = 30)
    private String capital;
    
    /**
     * Comma-separated list of non-capital cities.
     */
    @Column(length = 150)
    private String cities;
    
    protected EntityState() {}
    
    public EntityState(String name, String capital, String cities) {
        this.name = name;
        this.capital = capital;
        this.cities = cities;
    }
    
    public EntityState(NonEntityState nonEntityState) {
        this.name = nonEntityState.getName();
        this.capital = nonEntityState.getCapital();
        this.cities = String.join(",", nonEntityState.getCities());
    }
    
    
    @Override
    public String toString() {
        return String.format("EntityState[id=%d, name=%s, capital=%s, cities=%s]", id, name, capital, cities);
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCapital() {
        return capital;
    }
    
    public String getCities() {
        return cities;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCapital(String capital) {
        this.capital = capital;
    }
    
    public void setCities(String cities) {
        this.cities = cities;
    }
}
