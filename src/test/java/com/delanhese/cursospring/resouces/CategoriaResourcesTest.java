package com.delanhese.cursospring.resouces;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.delanhese.cursospring.SpringbootionicApplication;
import com.delanhese.cursospring.resources.CategoriaResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = SpringbootionicApplication.class)
@ActiveProfiles("test")
public class CategoriaResourcesTest{

	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaResource categoriaResource;
	
	@Before
	public void getCategorias() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(categoriaResource).build();
	}
	
	@Test
	public void testGETFindCategoriaResources() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/find")).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
