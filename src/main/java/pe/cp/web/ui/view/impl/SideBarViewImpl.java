package pe.cp.web.ui.view.impl;


import pe.cp.web.ui.handler.ISideBarHandler;
import pe.cp.web.ui.handler.impl.SideBarController;
import pe.cp.web.ui.view.ISideBarView;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SideBarViewImpl extends VerticalLayout implements ISideBarView {
	
	CssLayout menu = new CssLayout();
	ISideBarHandler handler;
	Label userName;
	
	public SideBarViewImpl(){
		handler = new SideBarController(this);
		
		addStyleName("sidebar");
        setWidth(null);
        setHeight("100%");
        
        // Branding element
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setStyleName("branding");
        Image imgLogo = new Image(null, new ThemeResource("img/logo-controlparking.png"));
        //imgLogo.setWidth("100px");
        //imgLogo.setHeight("75px");
        logoLayout.addComponent(imgLogo);
        
        //Image imgLogo = new Image(null, new ThemeResource("img/logo-controlparking.png"));
        //imgLogo.setSizeFull();
        //addComponent(imgLogo);
        /*addComponent(new CssLayout() {
            {
                addStyleName("branding");
                //Image imgLogo = new Image(null, new ThemeResource("img/logo-controlparking.png"));
                //imgLogo.setSizeFull();
                //addComponent(imgLogo);
            }
        });*/
        
        // Main menu
        addComponent(menu);
        setExpandRatio(menu, 1);
        
        // User menu
        addComponent(new VerticalLayout() {
            {
                setSizeUndefined();
                addStyleName("user");
                Image profilePic = new Image(null, new ThemeResource("img/profile-pic.png"));
                profilePic.setWidth("34px");
                addComponent(profilePic);
                userName = new Label("");
                userName.setSizeUndefined();
                addComponent(userName);

                Command cmd = new Command() {
                    @Override
                    public void menuSelected(
                            MenuItem selectedItem) {
                        Notification
                                .show("Not implemented in this demo");
                    }
                };
                
                Button conf = new NativeButton("Configuracion");
                conf.setStyleName("icon-cog");
                addComponent(conf);
                conf.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						handler.irConfiguracion();
					}
				});
                
                //MenuBar settings = new MenuBar();
                //MenuItem settingsMenu = settings.addItem("",null);
                //settingsMenu.setStyleName("icon-cog");
                //settingsMenu.addItem("Settings", cmd);
                //settingsMenu.addItem("Preferences", cmd);
                //settingsMenu.addSeparator();
                //settingsMenu.addItem("Mi Cuenta", cmd);
                //addComponent(settings);

                Button exit = new NativeButton("Exit");
                exit.addStyleName("icon-cancel");
                exit.setDescription("Sign Out");
                addComponent(exit);
                exit.addClickListener(new ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        handler.logout();
                    }
                });
            }
        });
        
        
        for (final String view : new String[] { "main", "operaciones", "reportes",
                								"configuracion", "auditoria"}) {
            Button b = new NativeButton(view.substring(0, 1).toUpperCase()
                    + view.substring(1).replace('-', ' '));
            b.addStyleName("icon-" + view);
            b.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                	event.getButton().addStyleName("selected");
                    if (!UI.getCurrent().getNavigator().getState().equals("/" + view)){
                    	UI.getCurrent().getNavigator().navigateTo(view);
                    }
                }
            });

            menu.addComponent(b);            

//            viewNameToMenuButton.put("/" + view, b);
            menu.addStyleName("menu");
            menu.setHeight("100%");
        }
        
        handler.cargarDatos();
	}

	@Override
	public Label getLabelUsuario() {
		return this.userName;
	}	
}
