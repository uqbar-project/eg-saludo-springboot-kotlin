package org.uqbar.egsaludospringkotlin

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureJsonTesters
@WebMvcTest
@DisplayName("Dado un controller de saludo")
// en lugar de hacer esto...
// @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// ...que genera un contexto por cada test, utilizamos la anotación @AfterEach
class SaludoApplicationTests(@Autowired val mockMvc: MockMvc) {

	@Test
	fun `el saludo default es el que tiene el saludador`() {
		mockMvc.perform(get("/saludoDefault"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.mensaje").value("Hola mundo!"))
	}

	@Test
	fun `actualizar el saludo a un valor incorrecto produce un error de usuario`() {
		val message = mockMvc.perform(put("/saludoDefault").content("dodain"))
			.andExpect(status().isBadRequest)
			.andReturn().resolvedException?.message
		assertEquals("No se puede saludar a dodain", message)
	}

	@Test
	fun `actualizar el saludo a un valor ok actualiza correctamente`() {
		val nuevoSaludoDefault = "Hola San Martín!"
		mockMvc.perform(put("/saludoDefault").content(nuevoSaludoDefault))
			.andExpect(status().isOk)
		mockMvc.perform(get("/saludoDefault"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.mensaje").value(nuevoSaludoDefault))
	}

	@Test
	fun `el saludo custom produce un saludo especifico`() {
		mockMvc.perform(get("/saludo/manola"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.mensaje").value("Hola manola!"))
	}

	@AfterEach
	fun `volvemos a dejar el saludo por defecto como estaba`() {
		mockMvc.perform(put("/saludoDefault").content("Hola mundo!"))
			.andExpect(status().isOk)
	}
}
