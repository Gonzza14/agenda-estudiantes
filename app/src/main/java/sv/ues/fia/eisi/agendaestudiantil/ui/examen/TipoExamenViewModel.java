package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import androidx.lifecycle.ViewModel;

public class TipoExamenViewModel extends ViewModel {

    private int idTipoExamen;
    private String nombreTipoExamen;

    public TipoExamenViewModel() {
    }

    public TipoExamenViewModel(int idTipoExamen, String nombreTipoExamen) {
        this.idTipoExamen = idTipoExamen;
        this.nombreTipoExamen = nombreTipoExamen;
    }

    public int getIdTipoExamen() {
        return idTipoExamen;
    }

    public void setIdTipoExamen(int idTipoExamen) {
        this.idTipoExamen = idTipoExamen;
    }

    public String getNombreTipoExamen() {
        return nombreTipoExamen;
    }

    public void setNombreTipoExamen(String nombreTipoExamen) {
        this.nombreTipoExamen = nombreTipoExamen;
    }

    public String toString(){
        return  nombreTipoExamen;
    }
}
