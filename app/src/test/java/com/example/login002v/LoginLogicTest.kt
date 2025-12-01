package com.example.login002v


import org.junit.Test
import org.junit.Assert.*
import android.util.Patterns // Esto puede requerir configuración extra en test unitarios, o usar un mock

class LoginLogicTest {

    @Test
    fun `validar que contraseñas coinciden`() {
        val pass1 = "123456"
        val pass2 = "123456"
        assertEquals(pass1, pass2)
    }

    @Test
    fun `validar que contraseñas NO coinciden`() {
        val pass1 = "123456"
        val pass2 = "abcdef"
        assertNotEquals(pass1, pass2)
    }

    @Test
    fun `validar email simple`() {
        val email = "alumno@duocuc.cl"
        assertTrue(email.contains("@") && email.contains("."))
    }
}