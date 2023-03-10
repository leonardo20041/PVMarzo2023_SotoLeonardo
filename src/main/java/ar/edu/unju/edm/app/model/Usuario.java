package ar.edu.unju.edm.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Min(value = 1000000, message = "El DNI debe ser mayor que un millon")
	@Max(value = 99999999, message = "El DNI debe ser menor que 100 millones")
	private Long dni;
	
	@NotEmpty
	@Size(min = 3, max = 15)
	private String nombre;
	
	@NotEmpty
	@Size(min = 3, max = 15)
	private String apellido;
	
	@NotNull
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fechaNacimiento;
	
	@NotEmpty
	private String nacionalidad;
	
	@NotEmpty
	private String contrasena;
	
	@NotEmpty
	private String tipoUsuario;

	
	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
//	public LocalDate getFechaNacimiento() {
//		return fechaNacimiento;
//	}
//	public void setFechaNacimiento(LocalDate fechaNacimiento) {
//		this.fechaNacimiento = fechaNacimiento;
//	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
