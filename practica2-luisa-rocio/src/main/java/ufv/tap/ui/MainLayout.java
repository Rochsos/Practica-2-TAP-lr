package ufv.tap.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
//import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
//import com.vaadin.flow.server.PWA;

import ufv.tap.ui.vista.VistaGrafica;
import ufv.tap.ui.vista.VistaTarea;

/*@PWA(
	    name = "Vaadin",
	    shortName = "TAREA",
	    offlineResources = {
	        "./styles/offline.css",
	        "./images/offline.png"
	    },
	    enableInstallPrompt = false
	)*/
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout{

	public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin");
        logo.addClassName("logo");

        //Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);//, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Tareas", VistaTarea.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            listLink,
            new RouterLink("Grafica de Tareas", VistaGrafica.class)
        ));
    }
}
