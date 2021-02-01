package bo.gob.sin.sre.fac.frvcc.jsf.dto.beneficiario;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditoriaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long usuarioRegistroId = null;

    private Long usuarioUltimaModificacionId = null;

    private LocalDateTime fechaRegistro = null;

    private LocalDateTime fechaUltimaModificacion = null;

    private String estadoId = null;

    public AuditoriaDto() {
    }

    public Long getUsuarioRegistroId() {
        return this.usuarioRegistroId;
    }

    public void setUsuarioRegistroId(Long usuarioRegistroId) {
        this.usuarioRegistroId = usuarioRegistroId;
    }

    public Long getUsuarioUltimaModificacionId() {
        return this.usuarioUltimaModificacionId;
    }

    public void setUsuarioUltimaModificacionId(Long usuarioUltimaModificacionId) {
        this.usuarioUltimaModificacionId = usuarioUltimaModificacionId;
    }

    public LocalDateTime getFechaRegistro() {
        return this.fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaUltimaModificacion() {
        return this.fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public String getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }
}
