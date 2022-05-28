package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

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

    public static Comparator<PeriodoViewModel> dateComparator = new Comparator<PeriodoViewModel>() {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int result = 0;
        Date fecha1;
        Date fecha2;
        @Override
        public int compare(PeriodoViewModel p1, PeriodoViewModel p2) {
            try {
                fecha1 = f.parse(p1.getInicioPeriodo());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                fecha2 = f.parse(p2.getInicioPeriodo());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return fecha1.compareTo(fecha2);
        }
    };

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

    public String toString(){
        return tituloPeriodo;
    }
}