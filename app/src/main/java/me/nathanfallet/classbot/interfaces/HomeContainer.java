package me.nathanfallet.classbot.interfaces;

import me.nathanfallet.classbot.models.Cours;
import me.nathanfallet.classbot.models.Devoirs;

public interface HomeContainer {

    void showConfiguration();

    Cours[] getCours();

    Devoirs[] getDevoirs();

}
