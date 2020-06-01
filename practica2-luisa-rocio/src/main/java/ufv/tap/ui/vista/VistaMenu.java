package ufv.tap.ui.vista;

import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import ufv.tap.backend.controlador.ControladorListaTarea;
import ufv.tap.backend.controlador.ControladorTarea;
import ufv.tap.ui.MainLayout;

@PageTitle("Menu | Vaadin")
@Route(value = "menu", layout = MainLayout.class)
public class VistaMenu extends VerticalLayout{

	private final ControladorTarea controladorTarea;
    private final ControladorListaTarea controladorListaTarea;

    public VistaMenu(ControladorTarea controladorTarea,
    		ControladorListaTarea controladorListaTarea) {
        this.controladorTarea = controladorTarea;
        this.controladorListaTarea = controladorListaTarea;

        addClassName("menu-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
            getTareaStats(),
            getListasChart()
        );
    }

    private Span getTareaStats() {
        Span stats = new Span(controladorTarea.count() + " tareas");
        stats.addClassName("tarea-stats");

        return stats;
    }

    private Component getListasChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> stats = controladorListaTarea.getStats();
        stats.forEach((name, number) ->
            dataSeries.add(new DataSeriesItem(name, number)));

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}
