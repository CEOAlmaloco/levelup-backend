package com.example.levelupprueba.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.profile.*
import com.example.levelupprueba.model.usuario.*
import com.example.levelupprueba.ui.screens.profile.PerfilEditable
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import kotlin.test.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var repo: UsuarioRepository
    private lateinit var vm: ProfileViewModel
    private lateinit var mainVM: MainViewModel
    private val dispatcher = StandardTestDispatcher()

    private val usuarioBase = Usuario(
        id = "U1",
        nombre = "Andrés",
        apellidos = "Pérez",
        email = "andres@gmail.com",
        password = "1234",
        telefono = "987654321",
        fechaNacimiento = "2000-01-01",
        region = "Metropolitana",
        comuna = "Santiago",
        direccion = "Calle Falsa 123",
        referralCode = "ABC123",
        points = 50,
        role = "cliente",
        avatar = "avatar1.png"
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repo = mockk(relaxed = true)
        mainVM = mockk(relaxed = true)
        vm = ProfileViewModel(repo)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // cargarDatosUsuario
    @Test
    fun `cargarDatosUsuario carga los datos del usuario y marca Loaded`() = runTest {
        // Usuario en base
        coEvery { repo.getUsuarioById("U1") } returns usuarioBase

        // Ejecuta
        vm.cargarDatosUsuario("U1")
        advanceUntilIdle()

        // Estado cargado
        val estado = vm.estado.value
        assertEquals("Andrés", estado.nombre.valor)
        assertEquals("Pérez", estado.apellidos.valor)
        assertEquals("andres@gmail.com", estado.email.valor)
        assertEquals("987654321", estado.telefono.valor)
        assertEquals(ProfileStatus.Loaded, estado.profileStatus)

        // Verifica llamada
        coVerify { repo.getUsuarioById("U1") }
    }

    // actualizarPerfil
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `actualizarPerfil actualiza campos y validaciones`() = runTest {
        val editable = PerfilEditable(
            nombre = "Juan",
            apellidos = "Torres",
            telefono = "999999999",
            fechaNacimiento = "1999-01-01",
            region = "Maule",
            comuna = "Talca",
            direccion = "Nueva",
            avatar = "avatarNuevo.png"
        )

        // Ejecuta actualización
        vm.actualizarPerfil(editable)

        val estado = vm.estado.value
        assertEquals("Juan", estado.nombre.valor)
        assertEquals("Torres", estado.apellidos.valor)
        assertEquals("999999999", estado.telefono.valor)
        assertEquals("avatarNuevo.png", estado.avatar)
    }

    // guardarPerfil
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `guardarPerfil guarda correctamente cuando no hay errores`() = runTest {
        val editable = PerfilEditable(
            nombre = "Nuevo",
            apellidos = "Apellido",
            telefono = "987654321",
            fechaNacimiento = "2000-01-01",
            region = "Metropolitana",
            comuna = "Santiago",
            direccion = "Calle X",
            avatar = "nuevoAvatar.png"
        )

        // Usuario encontrado por email al guardar
        coEvery { repo.getUsuarioByEmail(any()) } returns usuarioBase

        // Ejecuta guardado
        vm.guardarPerfil(editable, mainVM)
        advanceUntilIdle()

        // Estado final
        assertEquals(ProfileStatus.Saved, vm.estado.value.profileStatus)

        // Verifica actualización de avatar global
        verify { mainVM.updateAvatar("nuevoAvatar.png") }

        // Verifica que se guardó el usuario
        coVerify { repo.saveUsuario(any()) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `guardarPerfil marca ValidationError si hay errores en los campos`() = runTest {
        val editable = PerfilEditable(
            nombre = "",             // Inválido
            apellidos = "Torres",
            telefono = "987654321",
            fechaNacimiento = "2000-01-01",
            region = "Metropolitana",
            comuna = "Santiago",
            direccion = "Calle X",
            avatar = "avatar.png"
        )

        vm.guardarPerfil(editable, mainVM)
        advanceUntilIdle()

        // Debe quedar en ValidationError
        assertTrue(vm.estado.value.profileStatus is ProfileStatus.ValidationError)

        // No se debe guardar en BD
        coVerify(exactly = 0) { repo.saveUsuario(any()) }
    }


    // eliminarUsuario
    @Test
    fun `eliminarUsuario elimina correctamente cuando el usuario existe`() = runTest {
        coEvery { repo.getUsuarioById("U1") } returns usuarioBase

        vm.eliminarUsuario("U1")
        advanceUntilIdle()

        assertEquals(ProfileStatus.Deleted, vm.estado.value.profileStatus)

        coVerify { repo.deleteUsuario(usuarioBase) }
    }

    @Test
    fun `eliminarUsuario marca Error cuando el usuario no existe`() = runTest {
        coEvery { repo.getUsuarioById("U1") } returns null

        vm.eliminarUsuario("U1")
        advanceUntilIdle()

        assertTrue(vm.estado.value.profileStatus is ProfileStatus.Error)
        coVerify(exactly = 0) { repo.deleteUsuario(any()) }
    }

    // resetProfileStatus
    @Test
    fun `resetProfileStatus vuelve a Idle`() {
        vm.resetProfileStatus()
        assertEquals(ProfileStatus.Idle, vm.estado.value.profileStatus)
    }
}
