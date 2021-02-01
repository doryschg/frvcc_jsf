package bo.gob.sin.sre.fac.frvcc.jsf.dto.beneficiario;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonaBeneficiarioDto extends  AuditoriaDto {


    private long perBeneficiarioId;
    private long personaId;
    private int tipoDocumentoIdentidadId;
    private String tipoDocumentoIdentidadDescripcion;
    private Integer lugarExpedicionId;
    private String lugarExpedicionDescripcion;
    private Integer tipoPersonaId;
    private String correoElectronico;
    private String numeroDocumento;
    private String codigoComplementario;
    private String celular;
    private String telefonoReferencia1;
    private String telefonoReferencia2;
    private int paisOrigenId;
    private String paisOrigenDescripcion;
    private int estadoCivilId;
    private String estadoCivilDescripcion;
    private int generoId;
    private String generoDescripcion;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String apellidoCasada;
    private LocalDateTime fechaNacimiento;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String modificadoId;
    private String origenId;
    private String estadoPerBeneficiarioId;

    public String getNombreCompleto(){
        String nombreCompleto = getNombres();
        nombreCompleto = null != primerApellido ? nombreCompleto + " " + getPrimerApellido() : "";
        return   null != segundoApellido ? nombreCompleto + " " + getSegundoApellido() : "";

    }

    public PersonaBeneficiarioDto() {
    }

    public long getPerBeneficiarioId() {
        return perBeneficiarioId;
    }

    public void setPerBeneficiarioId(long perBeneficiarioId) {
        this.perBeneficiarioId = perBeneficiarioId;
    }

    public long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(long personaId) {
        this.personaId = personaId;
    }

    public int getTipoDocumentoIdentidadId() {
        return tipoDocumentoIdentidadId;
    }

    public void setTipoDocumentoIdentidadId(int tipoDocumentoIdentidadId) {
        this.tipoDocumentoIdentidadId = tipoDocumentoIdentidadId;
    }

    public Integer getLugarExpedicionId() {
        return lugarExpedicionId;
    }

    public void setLugarExpedicionId(Integer lugarExpedicionId) {
        this.lugarExpedicionId = lugarExpedicionId;
    }

    public Integer getTipoPersonaId() {
        return tipoPersonaId;
    }

    public void setTipoPersonaId(Integer tipoPersonaId) {
        this.tipoPersonaId = tipoPersonaId;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCodigoComplementario() {
        return codigoComplementario;
    }

    public void setCodigoComplementario(String codigoComplementario) {
        this.codigoComplementario = codigoComplementario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefonoReferencia1() {
        return telefonoReferencia1;
    }

    public void setTelefonoReferencia1(String telefonoReferencia1) {
        this.telefonoReferencia1 = telefonoReferencia1;
    }

    public String getTelefonoReferencia2() {
        return telefonoReferencia2;
    }

    public void setTelefonoReferencia2(String telefonoReferencia2) {
        this.telefonoReferencia2 = telefonoReferencia2;
    }

    public int getPaisOrigenId() {
        return paisOrigenId;
    }

    public void setPaisOrigenId(int paisOrigenId) {
        this.paisOrigenId = paisOrigenId;
    }

    public int getEstadoCivilId() {
        return estadoCivilId;
    }

    public void setEstadoCivilId(int estadoCivilId) {
        this.estadoCivilId = estadoCivilId;
    }

    public int getGeneroId() {
        return generoId;
    }

    public void setGeneroId(int generoId) {
        this.generoId = generoId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getApellidoCasada() {
        return apellidoCasada;
    }

    public void setApellidoCasada(String apellidoCasada) {
        this.apellidoCasada = apellidoCasada;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getEstadoPerBeneficiarioId() {
        return estadoPerBeneficiarioId;
    }

    public void setEstadoPerBeneficiarioId(String estadoPerBeneficiarioId) {
        this.estadoPerBeneficiarioId = estadoPerBeneficiarioId;
    }

    public String getTipoDocumentoIdentidadDescripcion() {
        return tipoDocumentoIdentidadDescripcion;
    }

    public void setTipoDocumentoIdentidadDescripcion(String tipoDocumentoIdentidadDescripcion) {
        this.tipoDocumentoIdentidadDescripcion = tipoDocumentoIdentidadDescripcion;
    }

    public String getLugarExpedicionDescripcion() {
        return lugarExpedicionDescripcion;
    }

    public void setLugarExpedicionDescripcion(String lugarExpedicionDescripcion) {
        this.lugarExpedicionDescripcion = lugarExpedicionDescripcion;
    }

    public String getPaisOrigenDescripcion() {
        return paisOrigenDescripcion;
    }

    public void setPaisOrigenDescripcion(String paisOrigenDescripcion) {
        this.paisOrigenDescripcion = paisOrigenDescripcion;
    }

    public String getEstadoCivilDescripcion() {
        return estadoCivilDescripcion;
    }

    public void setEstadoCivilDescripcion(String estadoCivilDescripcion) {
        this.estadoCivilDescripcion = estadoCivilDescripcion;
    }

    public String getGeneroDescripcion() {
        return generoDescripcion;
    }

    public void setGeneroDescripcion(String generoDescripcion) {
        this.generoDescripcion = generoDescripcion;
    }

    public String getModificadoId() {
        return modificadoId;
    }

    public void setModificadoId(String modificadoId) {
        this.modificadoId = modificadoId;
    }

    public String getOrigenId() {
        return origenId;
    }

    public void setOrigenId(String origenId) {
        this.origenId = origenId;
    }
}
