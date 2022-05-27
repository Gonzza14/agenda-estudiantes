package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import androidx.lifecycle.ViewModel;

import java.sql.Date;

public class PeriodoViewModel extends ViewModel {
    private int idPeriodo;
    private String tituloPeriodo;
    private String inicioPeriodo;
    private String finPeriodo;

    public PeriodoViewModel() {

    }

    public PeriodoViewModel(int idPeriodo, String tituloPeriodo, String inicioPeriodo, String finPeriodo) {
        this.idPeriodo = idPeriodo;
        this.tituloPeriodo = tituloPeriodo;
        this.inicioPeriodo = inicioPeriodo;
        this.finPeriodo = finPeriodo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getTituloPeriodo() {
        return tituloPeriodo;
    }

    public void setTituloPeriodo(String tituloPeriodo) {
        this.tituloPeriodo = tituloPeriodo;
    }

    public String getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(String inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public String getFinPeriodo() {
        return finPeriodo;
    }

    public void setFinPeriodo(String finPeriodo) {
        this.finPeriodo = finPeriodo;
    }
}