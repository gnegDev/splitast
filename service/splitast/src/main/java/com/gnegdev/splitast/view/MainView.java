package com.gnegdev.splitast.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("") // Exposes this view at the root URL (e.g., http://localhost:8080/)
public class MainView extends VerticalLayout {

    public MainView() {
        // Create an H1 header component
        H1 helloWorldHeader = new H1("SplitAST web prototype");

        // Add the header to the layout
        add(helloWorldHeader);

        // Optional: Add a button with a notification
        // Button clickMeButton = new Button("Click me", e -> Notification.show("Hello, Vaadin user!"));
        // add(clickMeButton);
    }
}
