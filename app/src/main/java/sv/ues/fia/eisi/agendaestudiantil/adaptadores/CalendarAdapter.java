package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener){
        this.days = days;
        this.onItemListener = onItemListener;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size() > 15)
            layoutParams.height = (int) (parent.getHeight()*0.1);
        else
            layoutParams.height = (int) (parent.getHeight());
        return new CalendarViewHolder(days, view, onItemListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);
        if (date == null)
            holder.dayOfMonth.setText("");
        else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            if (date.equals(CalendarUtils.selectedDate.now()))
                holder.parentView.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ArrayList<LocalDate> days;
        public final View parentView;
        public final TextView dayOfMonth;
        private final CalendarAdapter.OnItemListener onItemListener;
        public CalendarViewHolder(ArrayList<LocalDate> days, @NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.days = days;
            parentView = itemView.findViewById(R.id.parentView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
        }
    }
    public interface OnItemListener{
        void onItemClick(int position, LocalDate date);
    }
}
