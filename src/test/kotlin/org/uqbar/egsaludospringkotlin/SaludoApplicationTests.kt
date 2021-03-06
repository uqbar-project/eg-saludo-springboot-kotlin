package org.uqbar.egsaludospringkotlin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
	import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureJsonTesters
@WebMvcTest
@DisplayName("Dado un controller de saludo")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SaludoApplicationTests(@Autowired val mockMvc: MockMvc) {

	@Test
	fun `el saludo default es el que tiene el saludador`() {
		mockMvc.perform(get("/saludoDefault"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.mensaje").value("Hola mundo!"))
	}

	@Test
	fun `actualizar el saludo a un valor incorrecto produce un error de usuario`() {
		mockMvc.perform(MockMvcRequestBuilders.put("/saludoDefault").content("dodain"))
			.andExpect(status().isBadRequest)
//		mockMvc no pasa el mensaje si hay un Bad Request
//			.andExpect(jsonPath("$.message").value("No se puede saludar a " + Saludador.DODAIN))
	}

	@Test
	fun `actualizar el saludo a un valor ok actualiza correctamente`() {
		val nuevoSaludoDefault = "Hola San Martín!"
		mockMvc.perform(MockMvcRequestBuilders.put("/saludoDefault").content(nuevoSaludoDefault))
			.andExpect(status().isOk)
		mockMvc.perform(MockMvcRequestBuilders.get("/saludoDefault"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.mensaje").value(nuevoSaludoDefault))
	}

	@Test
	fun `el saludo custom produce un saludo especifico`() {
		mockMvc.perform(MockMvcRequestBuilders.get("/saludo/manola"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.mensaje").value("Hola manola!"))
	}

}
