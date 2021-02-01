package bo.gob.sin.sre.fac.frvcc.jsf.dto;

public class RolDto {
    private static final long serialVersionUID = 1L;
    private Integer rolId;
    private String descripcion;
    private String estadoId;
    private Integer estadoRolId;
    private String descripcionEstado;
    private String rol;
    private Integer tipoAplicacionId;
    private Long opcionId;
    private Integer subsistemaId;
    private Long usuarioId;
    private Integer tipoRolId;
    private String descripcionSubsistema;
    private String descripcionOpcion;
    private String descripcionTipoAplicacion;
    private String url;

    public RolDto() {
    }

    public Integer getRolId() {
        return this.rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getTipoAplicacionId() {
        return this.tipoAplicacionId;
    }

    public void setTipoAplicacionId(Integer tipoAplicacionId) {
        this.tipoAplicacionId = tipoAplicacionId;
    }

    public Long getOpcionId() {
        return this.opcionId;
    }

    public void setOpcionId(Long opcionId) {
        this.opcionId = opcionId;
    }

    public Integer getSubsistemaId() {
        return this.subsistemaId;
    }

    public void setSubsistemaId(Integer subsistemaId) {
        this.subsistemaId = subsistemaId;
    }

    public Long getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String toString() {
        return "RolDto [rolId=" + this.rolId + ", descripcion=" + this.descripcion + ", estadoId=" + this.estadoId + ", rol=" + this.rol + ", tipoAplicacionId=" + this.tipoAplicacionId + ", opcionId=" + this.opcionId + ", subsistemaId=" + this.subsistemaId + ", usuarioId=" + this.usuarioId + "]";
    }

    public Integer getEstadoRolId() {
        return this.estadoRolId;
    }

    public void setEstadoRolId(Integer estadoRolId) {
        this.estadoRolId = estadoRolId;
    }

    public String getDescripcionEstado() {
        return this.descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getDescripcionSubsistema() {
        return this.descripcionSubsistema;
    }

    public void setDescripcionSubsistema(String descripcionSubsistema) {
        this.descripcionSubsistema = descripcionSubsistema;
    }

    public String getDescripcionOpcion() {
        return this.descripcionOpcion;
    }

    public void setDescripcionOpcion(String descripcionOpcion) {
        this.descripcionOpcion = descripcionOpcion;
    }

    public String getDescripcionTipoAplicacion() {
        return this.descripcionTipoAplicacion;
    }

    public void setDescripcionTipoAplicacion(String descripcionTipoAplicacion) {
        this.descripcionTipoAplicacion = descripcionTipoAplicacion;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTipoRolId() {
        return this.tipoRolId;
    }

    public void setTipoRolId(Integer tipoRolId) {
        this.tipoRolId = tipoRolId;
    }
}
