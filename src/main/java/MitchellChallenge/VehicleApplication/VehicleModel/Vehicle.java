/**
 * MitchellChallenge.VehicleApplication.VehicleModel package inscribes a class Vehicle which describes the Properties, accessors and mutator methods of the Vehicle Object
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication.VehicleModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * class Vehicle which describes the Properties, accessors and mutator methods of the Vehicle Object
 */
@Entity
public class Vehicle {
    @Id
    public int id;
    @Column(name="year")
    public int year;
    @Column(name="make")
    public String make;
    @Column(name="model")
    public String model;

    public Vehicle(){
        super();
    }
    public Vehicle(int i, int i1, String s, String lamborgini) {
        this.id = i;
        this.year = i1;
        this.make = s;
        this.model = lamborgini;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}

