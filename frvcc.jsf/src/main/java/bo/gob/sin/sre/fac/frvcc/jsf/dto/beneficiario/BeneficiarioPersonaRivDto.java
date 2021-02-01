package bo.gob.sin.sre.fac.frvcc.jsf.dto.beneficiario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BeneficiarioPersonaRivDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private long beneficiarioId;
    private long personaId;
    private long codigoPersona;
    private String formatoNombreId;
    private String paisId;
    private Integer departamentoId;
    private String departamentoDescripcion;
    private String localidadDescripcion;
    private Integer alcaldiaId;
    private String direccion;
    private String tipoBeneficiarioId;
    private String nuaCua;
    private BigDecimal totalSalario;
    private String entidadFinancieraId;
    private String entidadFinancieraDescripcion;
    private String tipoCuentaBancariaId;
    private String cuentaBanco;
    private String monedaId;
    private int distritoCuentaId;
    private String estadoCuentaBancariaId;
    private String pagoAutomaticoId;
    private String estadoBeneficiarioId;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Long idBeneficiarioSigep;
    private int gestionRegistro;
    private BigDecimal salarioUno;
    private BigDecimal salarioDos;
    private BigDecimal salarioTres;
    private PersonaBeneficiarioDto personaBeneficiario;

    public BeneficiarioPersonaRivDto(long beneficiarioId, long personaId, long codigoPersona, String formatoNombreId, String paisId, Integer departamentoId, String departamentoDescripcion, String localidadDescripcion, Integer alcaldiaId, String direccion, String tipoBeneficiarioId, String nuaCua, BigDecimal totalSalario, String entidadFinancieraId, String tipoCuentaBancariaId, String cuentaBanco, String monedaId, int distritoCuentaId, String estadoCuentaBancariaId, String pagoAutomaticoId, String estadoBeneficiarioId, LocalDate fechaDesde, LocalDate fechaHasta, Long idBeneficiarioSigep, int gestionRegistro, BigDecimal salarioUno, BigDecimal salarioDos, BigDecimal salarioTres, PersonaBeneficiarioDto personaBeneficiario) {
        this.beneficiarioId = beneficiarioId;
        this.personaId = personaId;
        this.codigoPersona = codigoPersona;
        this.formatoNombreId = formatoNombreId;
        this.paisId = paisId;
        this.departamentoId = departamentoId;
        this.departamentoDescripcion = departamentoDescripcion;
        this.localidadDescripcion = localidadDescripcion;
        this.alcaldiaId = alcaldiaId;
        this.direccion = direccion;
        this.tipoBeneficiarioId = tipoBeneficiarioId;
        this.nuaCua = nuaCua;
        this.totalSalario = totalSalario;
        this.entidadFinancieraId = entidadFinancieraId;
        this.tipoCuentaBancariaId = tipoCuentaBancariaId;
        this.cuentaBanco = cuentaBanco;
        this.monedaId = monedaId;
        this.distritoCuentaId = distritoCuentaId;
        this.estadoCuentaBancariaId = estadoCuentaBancariaId;
        this.pagoAutomaticoId = pagoAutomaticoId;
        this.estadoBeneficiarioId = estadoBeneficiarioId;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.idBeneficiarioSigep = idBeneficiarioSigep;
        this.gestionRegistro = gestionRegistro;
        this.salarioUno = salarioUno;
        this.salarioDos = salarioDos;
        this.salarioTres = salarioTres;
        this.personaBeneficiario = personaBeneficiario;
    }

    public BeneficiarioPersonaRivDto() {
        super();
    }

    public long getBeneficiarioId() {
        return beneficiarioId;
    }

    public void setBeneficiarioId(long beneficiarioId) {
        this.beneficiarioId = beneficiarioId;
    }

    public long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(long personaId) {
        this.personaId = personaId;
    }

    public String getFormatoNombreId() {
        return formatoNombreId;
    }

    public void setFormatoNombreId(String formatoNombreId) {
        this.formatoNombreId = formatoNombreId;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public Integer getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Integer departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Integer getAlcaldiaId() {
        return alcaldiaId;
    }

    public void setAlcaldiaId(Integer alcaldiaId) {
        this.alcaldiaId = alcaldiaId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoBeneficiarioId() {
        return tipoBeneficiarioId;
    }

    public void setTipoBeneficiarioId(String tipoBeneficiarioId) {
        this.tipoBeneficiarioId = tipoBeneficiarioId;
    }

    public String getNuaCua() {
        return nuaCua;
    }

    public void setNuaCua(String nuaCua) {
        this.nuaCua = nuaCua;
    }

    public BigDecimal getTotalSalario() {
        return totalSalario;
    }

    public void setTotalSalario(BigDecimal totalSalario) {
        this.totalSalario = totalSalario;
    }

    public String getEntidadFinancieraId() {
        return entidadFinancieraId;
    }

    public void setEntidadFinancieraId(String entidadFinancieraId) {
        this.entidadFinancieraId = entidadFinancieraId;
    }

    public String getTipoCuentaBancariaId() {
        return tipoCuentaBancariaId;
    }

    public void setTipoCuentaBancariaId(String tipoCuentaBancariaId) {
        this.tipoCuentaBancariaId = tipoCuentaBancariaId;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public String getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(String monedaId) {
        this.monedaId = monedaId;
    }

    public int getDistritoCuentaId() {
        return distritoCuentaId;
    }

    public void setDistritoCuentaId(int distritoCuentaId) {
        this.distritoCuentaId = distritoCuentaId;
    }

    public String getEstadoCuentaBancariaId() {
        return estadoCuentaBancariaId;
    }

    public void setEstadoCuentaBancariaId(String estadoCuentaBancariaId) {
        this.estadoCuentaBancariaId = estadoCuentaBancariaId;
    }

    public String getPagoAutomaticoId() {
        return pagoAutomaticoId;
    }

    public void setPagoAutomaticoId(String pagoAutomaticoId) {
        this.pagoAutomaticoId = pagoAutomaticoId;
    }

    public String getEstadoBeneficiarioId() {
        return estadoBeneficiarioId;
    }

    public void setEstadoBeneficiarioId(String estadoBeneficiarioId) {
        this.estadoBeneficiarioId = estadoBeneficiarioId;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Long getIdBeneficiarioSigep() {
        return idBeneficiarioSigep;
    }

    public void setIdBeneficiarioSigep(Long idBeneficiarioSigep) {
        this.idBeneficiarioSigep = idBeneficiarioSigep;
    }


    public PersonaBeneficiarioDto getPersonaBeneficiario() {
        return personaBeneficiario;
    }

    public void setPersonaBeneficiario(PersonaBeneficiarioDto personaBeneficiario) {
        this.personaBeneficiario = personaBeneficiario;
    }

    public long getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(long codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public BigDecimal getSalarioUno() {
        return salarioUno;
    }

    public void setSalarioUno(BigDecimal salarioUno) {
        this.salarioUno = salarioUno;
    }

    public BigDecimal getSalarioDos() {
        return salarioDos;
    }

    public void setSalarioDos(BigDecimal salarioDos) {
        this.salarioDos = salarioDos;
    }

    public BigDecimal getSalarioTres() {
        return salarioTres;
    }

    public void setSalarioTres(BigDecimal salarioTres) {
        this.salarioTres = salarioTres;
    }

    public int getGestionRegistro() {
        return gestionRegistro;
    }

    public void setGestionRegistro(int gestionRegistro) {
        this.gestionRegistro = gestionRegistro;
    }

    public String getDepartamentoDescripcion() {
        return departamentoDescripcion;
    }

    public void setDepartamentoDescripcion(String departamentoDescripcion) {
        this.departamentoDescripcion = departamentoDescripcion;
    }

    public String getLocalidadDescripcion() {
        return localidadDescripcion;
    }

    public void setLocalidadDescripcion(String localidadDescripcion) {
        this.localidadDescripcion = localidadDescripcion;
    }

    public String getEntidadFinancieraDescripcion() {
        return entidadFinancieraDescripcion;
    }

    public void setEntidadFinancieraDescripcion(String entidadFinancieraDescripcion) {
        this.entidadFinancieraDescripcion = entidadFinancieraDescripcion;
    }
}
