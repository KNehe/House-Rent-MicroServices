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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class GetAllHousesInfo {


    private HouseServiceProxy houseServiceProxy;

    @Autowired
    public GetAllHousesInfo(HouseServiceProxy houseServiceProxy) {
        this.houseServiceProxy = houseServiceProxy;
    }

    @HystrixCommand(fallbackMethod = "getFallBackAllHouses",
            threadPoolKey = "allHousePool",
            threadPoolProperties = {
                    @HystrixProperty( name = "coreSize", value = "20"),
                    @HystrixProperty( name = "maxQueueSize", value = "10"),

            },
            commandProperties = {
                    @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value = "16000"),
                    @HystrixProperty( name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                    @HystrixProperty( name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty( name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
            )
    public ResponseEntity<?> getAllHouses(@PathVariable int page, @PathVariable int size){

        return  houseServiceProxy.getAllHouses( page, size );

    }

    public ResponseEntity<?> getFallBackAllHouses( @PathVariable int page, @PathVariable  int size){

        House house = new House();
        house.setContact("");
        house.setImage("");
        house.setLocation("");
        house.setNoOfBedRooms(0);
        house.setTotalNoOfRooms(0);
        house.setId(0L);

        return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(  CustomResponse.successResponse( house, "House not found"));

    }
}
