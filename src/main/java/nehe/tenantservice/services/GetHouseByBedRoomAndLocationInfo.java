package nehe.tenantservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import nehe.tenantservice.Proxys.HouseServiceProxy;
import nehe.tenantservice.models.House;
import nehe.tenantservice.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class GetHouseByBedRoomAndLocationInfo {

    private HouseServiceProxy houseServiceProxy;

    @Autowired
    public GetHouseByBedRoomAndLocationInfo(HouseServiceProxy houseServiceProxy) {
        this.houseServiceProxy = houseServiceProxy;
    }

    @HystrixCommand(fallbackMethod = "getFallBackHouseByBedRoomAndLocation",
            threadPoolKey = "bedroomLocationPool",
            threadPoolProperties = {
                    @HystrixProperty( name = "coreSize", value = "20"),
                    @HystrixProperty( name = "maxQueueSize", value = "10"),

            },
            commandProperties = {
                    @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value = "16000"),
                    @HystrixProperty( name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                    @HystrixProperty( name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty( name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public ResponseEntity<?> getAllHouseNoOfBedRoomsAndLocation(@PathVariable int bedrooms , @PathVariable  String location){

        return  houseServiceProxy.getAllHouseNoOfBedRoomsAndLocation( bedrooms, location);

    }

    public ResponseEntity<?> getFallBackHouseByBedRoomAndLocation(@PathVariable int bedrooms , @PathVariable  String location){

        House house = new House();
        house.setContact("");
        house.setImage("");
        house.setLocation( location );
        house.setNoOfBedRooms( bedrooms );
        house.setTotalNoOfRooms(0);
        house.setId(0L);

        return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(  CustomResponse.successResponse( house, "House not found"));

    }
}
