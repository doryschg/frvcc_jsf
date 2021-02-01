package bo.gob.sin.sre.fac.frvcc.jsf.model.compras;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.compras.CompraLargaDto;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="compraLargaModel")
@ViewScoped
public final  class CompraLargaModel {
   List<CompraLargaDto> compras = new ArrayList<>();

    public CompraLargaModel() {
    }

    public List<CompraLargaDto> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraLargaDto> compras) {
        this.compras = compras;
    }
    public void compraNueva(){
        CompraLargaDto compra = new CompraLargaDto();
        compras.add(compra);
    }

}
