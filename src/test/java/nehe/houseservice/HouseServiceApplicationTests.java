package nehe.houseservice;

import com.google.gson.Gson;
import nehe.houseservice.models.FailResponse;
import nehe.houseservice.models.House;
import nehe.houseservice.models.SuccessResponse;
import nehe.houseservice.services.HouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class HouseServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HouseService houseService;


	Gson gson = new Gson();

	String baseUrl = "/api/v1/house-service";

    House house = new House("Jinja Katwe",4,3,"https:aws/jpg","075345523");



	@Test
	void shouldAddHouse() throws  Exception{

		when( houseService.addHouse( any( House.class ) ) ) .thenReturn( house );

		MvcResult mvcResult = mockMvc.perform(
				post(baseUrl+"/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(house))
		        )
				.andExpect( status().isCreated())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson( result, SuccessResponse.class);

		Assertions.assertEquals("House added successfully",  response.getMessage() );
		Assertions.assertEquals("success",  response.getStatus() );

	}

	@Test
	void shouldNotAddHouse() throws  Exception {

		when(houseService.addHouse( any(House.class) )).thenReturn( null );

		MvcResult mvcResult = mockMvc.perform( post( baseUrl+"/")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
		                .content(gson.toJson(house)))
				.andExpect( status().isInternalServerError() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		FailResponse failResponse = gson.fromJson(result, FailResponse.class );

		Assertions.assertEquals( "House not saved",  failResponse.getMessage() );
		Assertions.assertEquals( "fail",  failResponse.getStatus() );




	}

	@Test
	void shouldEditHouse() throws  Exception{

		when( houseService.addHouse( any( House.class ) ) ) .thenReturn( house );

		MvcResult mvcResult = mockMvc.perform(
				patch(baseUrl+"/")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(gson.toJson(house))
		)
				.andExpect( status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson( result, SuccessResponse.class);

		Assertions.assertEquals("House edited successfully",  response.getMessage() );
		Assertions.assertEquals("success",  response.getStatus() );

	}

	@Test
	void shouldNotEditHouse() throws  Exception{

		when( houseService.addHouse( any( House.class ) ) ) .thenReturn( null );

		MvcResult mvcResult = mockMvc.perform(
				patch(baseUrl+"/")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(gson.toJson(house))
		)
				.andExpect( status().isInternalServerError())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		FailResponse response = gson.fromJson( result, FailResponse.class);

		Assertions.assertEquals("House not edited",  response.getMessage() );

	}

	@Test
	void shouldFindAllHouses() throws Exception{

		Pageable pageable = PageRequest.of( 1 , 1 );

		List<House> houseList = new ArrayList<>();
		houseList.add(house);

		Page<House> housePage = new PageImpl<>(houseList, pageable, houseList.size() );

		when(houseService.getAllHouses( anyInt() , anyInt() )).thenReturn( housePage );

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/page/1/size/1")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isOk() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson( result, SuccessResponse.class);

		Assertions.assertEquals(  "1 Houses retrieved", response.getMessage() );


	}

	@Test
	void shouldNotFindAllHouses() throws Exception{

		when(houseService.getAllHouses( anyInt() , anyInt() )).thenReturn( null );

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/page/1/size/1")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isInternalServerError() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson( result, SuccessResponse.class);

		Assertions.assertEquals(  "An error occurred", response.getMessage() );

	}


	@Test
	void shouldFindAllHousesByNoOfBedRooms() throws Exception{

		List<House> houses = new ArrayList<>();
		houses.add(house);

		when(houseService.getAllHousesByNoOfBedRooms(4)).thenReturn( houses);

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/bedrooms/4")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isOk() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson( result, SuccessResponse.class);

		Assertions.assertEquals(  "1 Houses retrieved", response.getMessage() );

	}

	@Test
	void shouldNotFindAllHousesByNoOfBedRooms() throws Exception{


		when(houseService.getAllHousesByNoOfBedRooms(4)).thenReturn( null);

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/bedrooms/4")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isInternalServerError() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		FailResponse response = gson.fromJson( result, FailResponse.class);

		Assertions.assertEquals(  "An error occurred", response.getMessage() );

	}


	@Test
	void shouldFindAllHousesByLocation() throws Exception{

		List<House> houses = new ArrayList<>();
		houses.add(house);

		when(houseService.getAllHousesByLocation("Jinja")).thenReturn( houses);

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/location/Jinja")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isOk() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		FailResponse response = gson.fromJson( result, FailResponse.class);

		Assertions.assertEquals(  "1 Houses retrieved", response.getMessage() );

	}

	@Test
	void shouldNotFindAllHousesByLocation() throws Exception{

		when(houseService.getAllHousesByLocation("Jinja")).thenReturn( null);

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/location/Jinja")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isInternalServerError() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		FailResponse response = gson.fromJson( result, FailResponse.class);

		Assertions.assertEquals(  "An error occurred", response.getMessage() );

	}

	@Test
	void shouldFindAllHousesByLocationAndBedrooms() throws Exception{

		List<House> houses = new ArrayList<>();
		houses.add(house);

		when(houseService.getAllHouseNoOfBedRoomsAndLocation(3,"Jinja")).thenReturn( houses);

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/bedrooms/3/location/Jinja")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isOk() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson( result, SuccessResponse.class);

		Assertions.assertEquals(  "1 Houses retrieved", response.getMessage() );

	}

	@Test
	void shouldNotFindAllHousesByLocationAndBedrooms() throws Exception{

		when(houseService.getAllHouseNoOfBedRoomsAndLocation(3,"Jinja")).thenReturn( null);

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/bedrooms/3/location/Jinja")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isInternalServerError() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		FailResponse response = gson.fromJson( result, FailResponse.class);

		Assertions.assertEquals(  "An error occurred", response.getMessage() );

	}

	@Test
	void shouldDeleteHouse() throws Exception{

		when(houseService.deleteHouse(3L)).thenReturn( true);

		MvcResult mvcResult = mockMvc.perform( delete( baseUrl + "/house/3")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isNoContent() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();


		Assertions.assertNull( null , result);

	}

	@Test
	void shouldNotDeleteHouseWhenNotExists() throws Exception{

		when(houseService.deleteHouse(3L)).thenReturn( false );

		MvcResult mvcResult = mockMvc.perform( delete( baseUrl + "/house/3")

				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isNoContent() )
				.andReturn();

	}


	@Test
	void shouldGetHouseById() throws Exception{

		when(houseService.getHouseById( anyLong() )).thenReturn( any(House.class) );

		MvcResult mvcResult = mockMvc.perform( get( baseUrl + "/house/3")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isOk() )
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		SuccessResponse response = gson.fromJson(result, SuccessResponse.class);

		Assertions.assertEquals( String.format("House with  Id %s found", 3)  , response.getMessage() );

	}



}
