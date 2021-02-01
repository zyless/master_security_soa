package covid.plattform.consumer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

    //Class for retrieven data from our user table
    @Id
    private String id;

    @Column(name="location")
    private String location;

    @Column(name="vorname")
    private String vorname;

    @Column(name="nachname")
    private String nachname;


    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
    
}
