package ufv.tap.ui.vista;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import ufv.tap.backend.controlador.ControladorListaTarea;
import ufv.tap.backend.controlador.ControladorTarea;
import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.modelo.Tarea;
import ufv.tap.ui.MainLayout;

@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Tareas | Vaadin")
public class VistaTarea extends VerticalLayout{

	TareaForm form;
	Grid<Tarea> grid = new Grid<>(Tarea.class);
	TextField filterText = new TextField();

	ControladorTarea controladorTarea;

	public VistaTarea(ControladorTarea controladorTarea,
			ControladorListaTarea controladorListaTarea) {
	        this.controladorTarea = controladorTarea;
	        addClassName("list-view");
	        setSizeFull();
	        configureGrid();


	        form = new TareaForm(controladorListaTarea.findAll());
	        form.addListener(TareaForm.SaveEvent.class, this::saveTarea);
	        form.addListener(TareaForm.DeleteEvent.class, this::deleteTarea);
	        form.addListener(TareaForm.CloseEvent.class, e -> closeEditor());

	        Div content = new Div(grid, form);
	        content.addClassName("content");
	        content.setSizeFull();

	        add(getToolBar(), content);
	        updateList();
	        closeEditor();
	    }

	private void deleteTarea(TareaForm.DeleteEvent evt) {
		controladorTarea.delete(evt.getTarea());
		updateList();
		closeEditor();
	}

	private void saveTarea(TareaForm.SaveEvent evt) {
		controladorTarea.save(evt.getTarea());
		updateList();
		closeEditor();
	}

	private HorizontalLayout getToolBar() {
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());

		Button addTareaButton = new Button("Add tarea", click -> addTarea());

		HorizontalLayout toolbar = new HorizontalLayout(filterText, addTareaButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	private void addTarea() {
		grid.asSingleSelect().clear();
		editTarea(new Tarea());
	}

	private void configureGrid() {
		grid.addClassName("tarea-grid");
		grid.setSizeFull();
		grid.removeColumnByKey("listatarea");
		grid.setColumns("nombre", "descripcion", "prioridad", "deadline", "estado");
		grid.addColumn(tarea -> {
			ListaTareas listaTareas = tarea.getListaTareas();
			return listaTareas == null ? "-" : listaTareas.getNombre();
		}).setHeader("Lista");

		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(evt -> editTarea(evt.getValue()));
	}

	private void editTarea(Tarea tarea) {
		if (tarea == null) {
			closeEditor();
		} else {
			form.setTarea(tarea);
			form.setVisible(true);
			addClassName("editing");
		}
	}

	private void closeEditor() {
		form.setTarea(null);
		form.setVisible(false);
		removeClassName("editing");
	}

	private void updateList() {
		grid.setItems(controladorTarea.findAll(filterText.getValue()));
	}
}
