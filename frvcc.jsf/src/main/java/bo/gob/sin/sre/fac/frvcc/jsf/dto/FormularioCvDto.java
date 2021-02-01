package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class FormularioCvDto implements Serializable{

	   private String id;
	    private String tipoFormularioId;
	    private Long nitCi;
	    private Long ifc;
	    private Long codigoDependiente;
	    private String razonSocial;
	    private String periodo;
	    private Integer expedicionCi;
	    private Integer mesPeriodo;
	    private Integer anioPeriodo;
	    private String nombreFormulario;
	    private Integer cantidadCompras;
	    private BigDecimal totalCompras;
	    private Integer cantidadComprasCfElec;
	    private Integer cantidadComprasCfIpn;
	    private Integer cantidadComprasCfOtras;
	    private Integer cantidadComprasSdCf;
	    private BigDecimal totalComprasCfElec;
	    private BigDecimal totalComprasCfIpn;
	    private BigDecimal totalComprasCfOtras;
	    private BigDecimal totalComprasSdCf;
	    private BigDecimal determinacionPagoCf;
	    private BigDecimal determinacionPagoCfIpn;
	    private BigDecimal determinacionPagoCfOtras;
	    private BigDecimal determinacionPagoSdCf;
	    private BigDecimal determinacionPago;
	    private Integer lugarDepartamento;
	    private String  tipoPresentacionId;
	    private String direccion;
	    private Long nitEmpleador;
	    private String nombreEmpleador;
	    private String direccionEmpleador;
	    @JsonDeserialize(using = LocalDateDeserializer.class)
	   	@JsonSerialize(using = LocalDateSerializer.class)
	    private LocalDate fechaPresentacion;
	    private String periodicidadId;
	    private String cantidadPeriodicidad;
	    private String numeroOrden;
	    private String estadoFormularioId;
	    @JsonDeserialize(using = LocalDateDeserializer.class)
	   	@JsonSerialize(using = LocalDateSerializer.class)
	    private LocalDate fechaEstado;

	    private Integer numeroEnvio;
	    private Long usuarioRegistro;
	    private LocalDateTime fechaRegistro;
	    private Long usuarioUltimaModificacion;
	    private LocalDateTime fechaUltimaModificacion;
	    private String estadoId;
	    private DatosEspecificosDto datosEspecificos;
	    private Integer numeroSucursal;
		private Long usuarioReceptor;
		private String loginUsuarioReceptor;
		private String nombreReceptor;
		private BigInteger totalComprasCfRed;
		private BigInteger totalComprasCfIpnRed;
		private BigInteger totalComprasCfOtrasRed;
		private BigInteger totalComprasSdCfRed;
		private BigInteger determinacionPagoCfRed;
		private BigInteger determinacionPagoCfIpnRed;
		private BigInteger determinacionPagoCfOtrasRed;
		private BigInteger determinacionPagoSdCfRed;
	    private List<String> compras;
	    private String descripcionMes;
	    private String tipoUsoId;
		private String tipoUsoDescripcion;
	    
	    public FormularioCvDto()
	    {
	    	datosEspecificos=new DatosEspecificosDto();
	    }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTipoFormularioId() {
			return tipoFormularioId;
		}

		public void setTipoFormularioId(String tipoFormularioId) {
			this.tipoFormularioId = tipoFormularioId;
		}

		public Long getNitCi() {
			return nitCi;
		}

		public void setNitCi(Long nitCi) {
			this.nitCi = nitCi;
		}

		public Long getIfc() {
			return ifc;
		}

		public void setIfc(Long ifc) {
			this.ifc = ifc;
		}

		public Long getCodigoDependiente() {
			return codigoDependiente;
		}

		public void setCodigoDependiente(Long codigoDependiente) {
			this.codigoDependiente = codigoDependiente;
		}

		public String getRazonSocial() {
			return razonSocial;
		}

		public void setRazonSocial(String razonSocialVc) {
			this.razonSocial = razonSocialVc;
		}

		public Integer getExpedicionCi() {
			return expedicionCi;
		}

		public void setExpedicionCi(Integer expedicionCi) {
			this.expedicionCi = expedicionCi;
		}

		public Integer getMesPeriodo() {
			return mesPeriodo;
		}

		public void setMesPeriodo(Integer mesPeriodo) {
			this.mesPeriodo = mesPeriodo;
		}

		public Integer getAnioPeriodo() {
			return anioPeriodo;
		}

		public void setAnioPeriodo(Integer anioPeriodo) {
			this.anioPeriodo = anioPeriodo;
		}

		public String getNombreFormulario() {
			return nombreFormulario;
		}

		public void setNombreFormulario(String nombreFormulario) {
			this.nombreFormulario = nombreFormulario;
		}

		public Integer getCantidadCompras() {
			return cantidadCompras;
		}

		public void setCantidadCompras(Integer cantidadCompras) {
			this.cantidadCompras = cantidadCompras;
		}

		public BigDecimal getTotalCompras() {
			return totalCompras;
		}

		public void setTotalCompras(BigDecimal totalCompras) {
			this.totalCompras = totalCompras;
		}

		public Integer getCantidadComprasCfElec() {
			return cantidadComprasCfElec;
		}

		public void setCantidadComprasCfElec(Integer cantidadComprasCfElec) {
			this.cantidadComprasCfElec = cantidadComprasCfElec;
		}

		public Integer getCantidadComprasCfIpn() {
			return cantidadComprasCfIpn;
		}

		public void setCantidadComprasCfIpn(Integer cantidadComprasCfIpn) {
			this.cantidadComprasCfIpn = cantidadComprasCfIpn;
		}

		public Integer getCantidadComprasCfOtras() {
			return cantidadComprasCfOtras;
		}

		public void setCantidadComprasCfOtras(Integer cantidadComprasCfOtras) {
			this.cantidadComprasCfOtras = cantidadComprasCfOtras;
		}

		public Integer getCantidadComprasSdCf() {
			return cantidadComprasSdCf;
		}

		public void setCantidadComprasSdCf(Integer cantidadComprasSdCf) {
			this.cantidadComprasSdCf = cantidadComprasSdCf;
		}

		public BigDecimal getTotalComprasCfElec() {
			return totalComprasCfElec;
		}

		public void setTotalComprasCfElec(BigDecimal totalComprasCfElec) {
			this.totalComprasCfElec = totalComprasCfElec;
		}

		public BigDecimal getTotalComprasCfIpn() {
			return totalComprasCfIpn;
		}

		public void setTotalComprasCfIpn(BigDecimal totalComprasCfIpn) {
			this.totalComprasCfIpn = totalComprasCfIpn;
		}

		public BigDecimal getTotalComprasCfOtras() {
			return totalComprasCfOtras;
		}

		public void setTotalComprasCfOtras(BigDecimal totalComprasCfOtras) {
			this.totalComprasCfOtras = totalComprasCfOtras;
		}

		public BigDecimal getTotalComprasSdCf() {
			return totalComprasSdCf;
		}

		public void setTotalComprasSdCf(BigDecimal totalComprasSdCf) {
			this.totalComprasSdCf = totalComprasSdCf;
		}

		public BigDecimal getDeterminacionPagoCf() {
			return determinacionPagoCf;
		}

		public void setDeterminacionPagoCf(BigDecimal determinacionPagoCf) {
			this.determinacionPagoCf = determinacionPagoCf;
		}

		public BigDecimal getDeterminacionPagoCfIpn() {
			return determinacionPagoCfIpn;
		}

		public void setDeterminacionPagoCfIpn(BigDecimal determinacionPagoCfIpn) {
			this.determinacionPagoCfIpn = determinacionPagoCfIpn;
		}

		public BigDecimal getDeterminacionPagoCfOtras() {
			return determinacionPagoCfOtras;
		}

		public void setDeterminacionPagoCfOtras(BigDecimal determinacionPagoCfOtras) {
			this.determinacionPagoCfOtras = determinacionPagoCfOtras;
		}

		public BigDecimal getDeterminacionPagoSdCf() {
			return determinacionPagoSdCf;
		}

		public void setDeterminacionPagoSdCf(BigDecimal determinacionPagoSdCf) {
			this.determinacionPagoSdCf = determinacionPagoSdCf;
		}

		public BigDecimal getDeterminacionPago() {
			return determinacionPago;
		}

		public void setDeterminacionPago(BigDecimal determinacionPago) {
			this.determinacionPago = determinacionPago;
		}

		public Integer getLugarDepartamento() {
			return lugarDepartamento;
		}

		public void setLugarDepartamento(Integer lugarDepartamento) {
			this.lugarDepartamento = lugarDepartamento;
		}

		public String getTipoPresentacionId() {
			return tipoPresentacionId;
		}

		public void setTipoPresentacionId(String tipoPresentacionId) {
			this.tipoPresentacionId = tipoPresentacionId;
		}

		public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		public Long getNitEmpleador() {
			return nitEmpleador;
		}

		public void setNitEmpleador(Long nitEmpleador) {
			this.nitEmpleador = nitEmpleador;
		}

		public String getNombreEmpleador() {
			return nombreEmpleador;
		}

		public void setNombreEmpleador(String nombreEmpleadorVc) {
			this.nombreEmpleador = nombreEmpleadorVc;
		}

		public String getDireccionEmpleador() {
			return direccionEmpleador;
		}

		public void setDireccionEmpleador(String direccionEmpleador) {
			this.direccionEmpleador = direccionEmpleador;
		}

		public LocalDate getFechaPresentacion() {
			return fechaPresentacion;
		}

		public void setFechaPresentacion(LocalDate fechaPresentacion) {
			this.fechaPresentacion = fechaPresentacion;
		}

		public String getPeriodicidadId() {
			return periodicidadId;
		}

		public void setPeriodicidadId(String periodicidadIdVc) {
			this.periodicidadId = periodicidadIdVc;
		}

		public String getCantidadPeriodicidad() {
			return cantidadPeriodicidad;
		}

		public void setCantidadPeriodicidad(String cantidadPeriodicidadVc) {
			this.cantidadPeriodicidad = cantidadPeriodicidadVc;
		}

		public String getNumeroOrden() {
			return numeroOrden;
		}

		public void setNumeroOrden(String numeroOrden) {
			this.numeroOrden = numeroOrden;
		}

		public String getEstadoFormularioId() {
			return estadoFormularioId;
		}

		public void setEstadoFormularioId(String estadoFormularioId) {
			this.estadoFormularioId = estadoFormularioId;
		}

		public LocalDate getFechaEstado() {
			return fechaEstado;
		}

		public void setFechaEstado(LocalDate fechaEstado) {
			this.fechaEstado = fechaEstado;
		}

		public Integer getNumeroEnvio() {
			return numeroEnvio;
		}

		public void setNumeroEnvio(Integer numeroEnvio) {
			this.numeroEnvio = numeroEnvio;
		}

		public Long getUsuarioRegistro() {
			return usuarioRegistro;
		}

		public void setUsuarioRegistro(Long usuarioRegistro) {
			this.usuarioRegistro = usuarioRegistro;
		}

		public LocalDateTime getFechaRegistro() {
			return fechaRegistro;
		}

		public void setFechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		public Long getUsuarioUltimaModificacion() {
			return usuarioUltimaModificacion;
		}

		public void setUsuarioUltimaModificacion(Long usuarioUltimaModificacion) {
			this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		}

		public LocalDateTime getFechaUltimaModificacion() {
			return fechaUltimaModificacion;
		}

		public void setFechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
			this.fechaUltimaModificacion = fechaUltimaModificacion;
		}

		public String getEstadoId() {
			return estadoId;
		}

		public void setEstadoId(String estadoId) {
			this.estadoId = estadoId;
		}

		public DatosEspecificosDto getDatosEspecificos() {
			return datosEspecificos;
		}

		public void setDatosEspecificos(DatosEspecificosDto datosEspecificos) {
			this.datosEspecificos = datosEspecificos;
		}

		public List<String> getCompras() {
			return compras;
		}

		public void setCompras(List<String> compras) {
			this.compras = compras;
		}

		public String getPeriodo() {
			return periodo;
		}

		public void setPeriodo(String periodo) {
			this.periodo = periodo;
		}

		public Integer getNumeroSucursal() {
			return numeroSucursal;
		}

		public void setNumeroSucursal(Integer numeroSucursal) {
			this.numeroSucursal = numeroSucursal;
		}

		public Long getUsuarioReceptor() {
			return usuarioReceptor;
		}

		public void setUsuarioReceptor(Long usuarioReceptor) {
			this.usuarioReceptor = usuarioReceptor;
		}

		public String getLoginUsuarioReceptor() {
			return loginUsuarioReceptor;
		}

		public void setLoginUsuarioReceptor(String loginUsuarioReceptor) {
			this.loginUsuarioReceptor = loginUsuarioReceptor;
		}

		public String getNombreReceptor() {
			return nombreReceptor;
		}

		public void setNombreReceptor(String nombreReceptor) {
			this.nombreReceptor = nombreReceptor;
		}

	public BigInteger getTotalComprasCfRed() {
		return totalComprasCfRed;
	}

	public void setTotalComprasCfRed(BigInteger totalComprasCfRed) {
		this.totalComprasCfRed = totalComprasCfRed;
	}

	public BigInteger getTotalComprasCfIpnRed() {
		return totalComprasCfIpnRed;
	}

	public void setTotalComprasCfIpnRed(BigInteger totalComprasCfIpnRed) {
		this.totalComprasCfIpnRed = totalComprasCfIpnRed;
	}

	public BigInteger getTotalComprasCfOtrasRed() {
		return totalComprasCfOtrasRed;
	}

	public void setTotalComprasCfOtrasRed(BigInteger totalComprasCfOtrasRed) {
		this.totalComprasCfOtrasRed = totalComprasCfOtrasRed;
	}

	public BigInteger getTotalComprasSdCfRed() {
		return totalComprasSdCfRed;
	}

	public void setTotalComprasSdCfRed(BigInteger totalComprasSdCfRed) {
		this.totalComprasSdCfRed = totalComprasSdCfRed;
	}

	public BigInteger getDeterminacionPagoCfRed() {
		return determinacionPagoCfRed;
	}

	public void setDeterminacionPagoCfRed(BigInteger determinacionPagoCfRed) {
		this.determinacionPagoCfRed = determinacionPagoCfRed;
	}

	public BigInteger getDeterminacionPagoCfIpnRed() {
		return determinacionPagoCfIpnRed;
	}

	public void setDeterminacionPagoCfIpnRed(BigInteger determinacionPagoCfIpnRed) {
		this.determinacionPagoCfIpnRed = determinacionPagoCfIpnRed;
	}

	public BigInteger getDeterminacionPagoCfOtrasRed() {
		return determinacionPagoCfOtrasRed;
	}

	public void setDeterminacionPagoCfOtrasRed(BigInteger determinacionPagoCfOtrasRed) {
		this.determinacionPagoCfOtrasRed = determinacionPagoCfOtrasRed;
	}

	public BigInteger getDeterminacionPagoSdCfRed() {
		return determinacionPagoSdCfRed;
	}

	public void setDeterminacionPagoSdCfRed(BigInteger determinacionPagoSdCfRed) {
		this.determinacionPagoSdCfRed = determinacionPagoSdCfRed;
	}

	public String getDescripcionMes() {
			return descripcionMes;
		}

		public void setDescripcionMes(String descripcionMes) {
			this.descripcionMes = descripcionMes;
		}

	public String getTipoUsoId() {
		return tipoUsoId;
	}

	public void setTipoUsoId(String tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public String getTipoUsoDescripcion() {
		return tipoUsoDescripcion;
	}

	public void setTipoUsoDescripcion(String tipoUsoDescripcion) {
		this.tipoUsoDescripcion = tipoUsoDescripcion;
	}


}
