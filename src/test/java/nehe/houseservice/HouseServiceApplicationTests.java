package nehe.houseservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
class HouseServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	void testHello() throws  Exception{

		MvcResult mvcResult = mockMvc.perform( get("/"))
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		Assertions.assertEquals(result , "hello");
	}

}
