package bo.gob.sin.sre.fac.frvcc.jsf.util;

import java.time.LocalDate;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;

public class ValidadorFechaCompraFormulario {
	
	public boolean verificaFechaValidaEnRango(FormularioCvDto pFormulario,ComprasCvDto pCompra)
	{
		boolean vRespuesta=false;
		LocalDate vfechaDesde=LocalDate.now();
		LocalDate vfechaHasta=LocalDate.now();
		switch (pFormulario.getTipoFormularioId()) {
		case ParametrosFRVCC.TIPO_FORMULARIO_110:
			vfechaDesde = pFormulario.getFechaPresentacion().plusDays(-Integer.valueOf(pFormulario.getCantidadPeriodicidad()));
			vfechaHasta = pFormulario.getFechaPresentacion();
			break;
		case ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F610:
			vfechaHasta=LocalDate.of(pFormulario.getAnioPeriodo(), pFormulario.getMesPeriodo(),
					obtenerCantidadDiasMes(pFormulario.getAnioPeriodo(),pFormulario.getMesPeriodo()));
			vfechaDesde = vfechaHasta.plusDays(-Integer.valueOf(pFormulario.getCantidadPeriodicidad()));
			break;
		case ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F510:
			vfechaHasta=LocalDate.of(pFormulario.getAnioPeriodo(), 12, 31);
			vfechaDesde=LocalDate.of(pFormulario.getAnioPeriodo(), 1, 1);
			break;
		case ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F702:
			vfechaHasta=LocalDate.of(pFormulario.getAnioPeriodo(), pFormulario.getMesPeriodo(),
					obtenerCantidadDiasMes(pFormulario.getAnioPeriodo(),pFormulario.getMesPeriodo()));
			vfechaDesde=LocalDate.of(pFormulario.getAnioPeriodo(), vfechaHasta.plusMonths(-2).getMonth(),
					1);
			break;
		default:
			break;
		}
		if(pCompra.getFechaFactura().isBefore(vfechaHasta) && pCompra.getFechaFactura().isAfter(vfechaDesde))
		{
			vRespuesta=true;
		}
		
		return vRespuesta;
	}
	
	public int obtenerCantidadDiasMes(Integer pGestion,Integer pMes)
	{	Integer dias=0;
		if (pGestion != null && pMes != null) {
			boolean biciesto;
			if ((pGestion % 4 == 0) && ((pGestion % 100 != 0) || (pGestion % 400 == 0)))
				biciesto=false;
			else
				biciesto=true;
			
			switch (pMes) {
			  case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			    dias = 31;
			    break;
			  case 4: case 6: case 9: case 11:
				  dias = 30;
			    break;
			  case 2:	
				  if(biciesto)
			    dias = 29;
			    else
			    	dias=28;
			    break;			
			}
			
		}
		return dias;
	}
}
