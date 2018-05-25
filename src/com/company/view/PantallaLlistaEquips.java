package com.company.view;

import com.company.manager.ManagerEquips;
import com.company.view.widget.LectorTeclat;
import com.company.view.widget.Missatge;
import com.company.view.widget.WidgetEquips;

import java.io.IOException;

public class PantallaLlistaEquips {
    public static void mostrar() throws IOException {
        Missatge.mostrarTitol("MARATHON :: Equips :: Llista");

        WidgetEquips.llistar(ManagerEquips.obtenirLlistaEquips());

        LectorTeclat.llegirContinuar();
    }
}
