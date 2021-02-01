package bo.gob.sin.sre.fac.frvcc.jsf.controller.compras;

import bo.gob.sin.sre.fac.frvcc.jsf.controller.ClientesRestController;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.compras.CompraLargaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.compras.CompraLargaModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "registroCompraLargaController")
@ViewScoped
public class RegistroCompraLargaController {
    @ManagedProperty(value = "#{compraLargaModel}")
    private CompraLargaModel compraLargaModel;
    @ManagedProperty(value = "#{clientesRestController}")
    private ClientesRestController clientesRestController;

    public ClientesRestController getClientesRestController() {
        return clientesRestController;
    }

    public void setClientesRestController(ClientesRestController clientesRestController) {
        this.clientesRestController = clientesRestController;
    }

    public CompraLargaModel getCompraLargaModel() {
        return compraLargaModel;
    }

    public void setCompraLargaModel(CompraLargaModel compraLargaModel) {
        this.compraLargaModel = compraLargaModel;
    }

    public void init() {
        compraLargaModel.compraNueva();
    }

    public void guardar(ActionEvent actionEvent) {
        CompraLargaDto compra = (CompraLargaDto) actionEvent.getComponent().getAttributes().get("compra");
        compraLargaModel.compraNueva();
    }


}
