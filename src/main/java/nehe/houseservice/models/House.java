package nehe.houseservice.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotNull
    @NotEmpty
    private  String location;

    @NotNull
    private int noOfBedRooms;

    @NotNull
    private int totalNoOfRooms;

    @NotNull
    @NotEmpty
    private String image;

    @NotNull
    @NotEmpty
    private  String contact;

    public House() {
    }

    public House( String location, int noOfBedRooms, int totalNoOfRooms, String image, String contact) {
        this.location = location;
        this.noOfBedRooms = noOfBedRooms;
        this.totalNoOfRooms = totalNoOfRooms;
        this.image = image;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNoOfBedRooms() {
        return noOfBedRooms;
    }

    public void setNoOfBedRooms(int noOfBedRooms) {
        this.noOfBedRooms = noOfBedRooms;
    }

    public int getTotalNoOfRooms() {
        return totalNoOfRooms;
    }

    public void setTotalNoOfRooms(int totalNoOfRooms) {
        this.totalNoOfRooms = totalNoOfRooms;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
