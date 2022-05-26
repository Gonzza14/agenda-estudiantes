package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import androidx.lifecycle.ViewModel;

import java.sql.Date;

public class PeriodoViewModel extends ViewModel {
    private int idPeriodo;
    private String tituloPeriodo;
    private Date inicioPeriodo;
    private Date finPeriodo;

    public PeriodoViewModel() {

    }

    public PeriodoViewModel(int idPeriodo, String tituloPeriodo, Date inicioPeriodo, Date finPeriodo) {
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

    public Date getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(Date inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public Date getFinPeriodo() {
        return finPeriodo;
    }

    public void setFinPeriodo(Date finPeriodo) {
        this.finPeriodo = finPeriodo;
    }
}