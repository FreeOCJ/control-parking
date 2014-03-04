package pe.cp.web.ui.view.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.handler.ISideBarHandler;
import pe.cp.web.ui.handler.impl.SideBarController;
import pe.cp.web.ui.view.ISideBarView;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SideBarViewImpl extends VerticalLayout implements ISideBarView {
	
	CssLayout menu = new CssLayout();
	ISideBarHandler handler;
	Label userName;
	
	public SideBarViewImpl(){
		init();
		handler = new SideBarController(this);
	}

	@Override
	public Label getLabelUsuario() {
		return this.userName;
	}

	@Override
	public void init() {
		addStyleName("sidebar");
        setWidth(null);
        setHeight("100%");
        
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
                
                Button conf = new NativeButton("Configuracion");
                conf.setStyleName("icon-cog");
                addComponent(conf);
                conf.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						handler.irConfiguracion();
					}
				});

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
        
        for (final String view : new String[] { "main", "operaciones", "reportes","configuracion", "auditoria"}) {
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
			
			menu.addStyleName("menu");
			menu.setHeight("100%");
        }
	}	
}
