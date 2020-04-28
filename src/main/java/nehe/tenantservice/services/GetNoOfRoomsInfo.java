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
public class GetNoOfRoomsInfo {

    private HouseServiceProxy houseServiceProxy;

    @Autowired
    public GetNoOfRoomsInfo(HouseServiceProxy houseServiceProxy) {
        this.houseServiceProxy = houseServiceProxy;
    }

    @HystrixCommand(fallbackMethod = "getFallBackHouseByTotalNoOfRooms",
            threadPoolKey = "noOfRoomsPool",
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
    public ResponseEntity<?> getHouseByTotalNoOfRooms(@PathVariable int rooms){

        return  houseServiceProxy.getHouseByTotalNoOfRooms( rooms );

    }

    public ResponseEntity<?> getFallBackHouseByTotalNoOfRooms(@PathVariable int rooms){

        House house = new House();
        house.setContact("");
        house.setImage("");
        house.setLocation("");
        house.setNoOfBedRooms(0);
        house.setTotalNoOfRooms(rooms);
        house.setId(0L);

        return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(  CustomResponse.successResponse( house, "House not found"));

    }
}
