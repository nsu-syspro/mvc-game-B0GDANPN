import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;

/**
 * Classe per crear objectes de tipus "Helicopter".
 *
 * @author Jose Garvin Victoria
 *
 */
public class Helicopter extends ObjecteEnMoviment {

    /**
     * Velocitat dels helicopters.
     */
    private static final int VELOCITAT = 2;

    /**
     * Arraylist amb les imatges de l'helicopter.
     */
    private ArrayList<GImage> imatges;

    /**
     * Index corresponent a la imatge actual de l'helicopter.
     */
    private int indexImatge;

    /**
     * Ubicació inicial de l'helicopter. 0/Esquerra 1/Dreta.
     */
    private int ubicacio;

    /**
     * Constructor per defecte.
     */
    Helicopter() {

    }

    /**
     * Constructor per a objectes de tipus "Helicopter".
     *
     * @param imatgesHeli
     *            --> ArrayList amb les imatges de l'helicopter.
     */
    Helicopter(final ArrayList<GImage> imatgesHeli) {
        setImatge(imatgesHeli.get(0));
        indexImatge = 0;
        imatges = imatgesHeli;
        ubicacio = generarRandom();
        this.setHaSortit(false);

    }

    /**
     * Mètode per inicialitzar els helicopters.
     *
     * @param finestra
     *            --> Finestra del joc.
     */

    public final void startPlay(final Main finestra) {

        if (this.estaDinsFinestra(finestra, getImatge())) {
            this.moureHelicopter(finestra);
        } else {
            this.setHaSortit(true);
        }

    }

    /**
     * Mètode per moure els helicopters.
     *
     * @param finestra
     *            --> Finestra del joc.
     */
    final void moureHelicopter(final Main finestra) {
        if (ubicacio == 0) {
            this.getImatge().move(VELOCITAT, 0);

        } else {
            this.getImatge().move(VELOCITAT * -1, 0);

        }

        canviImatge(finestra);

    }

    /**
     * Mètode per donar l'efecte de moviment a l'helicopter.
     *
     * @param main
     *            --> Finestra del joc.
     */
    final void canviImatge(final Main main) {
        if (indexImatge == 0) {

            double x = getImatge().getX();
            double y = getImatge().getY();
            main.remove(getImatge());
            setImatge(imatges.get(1));

            getImatge().setLocation(x, y);

            indexImatge = 1;

        } else {
            double x = getImatge().getX();
            double y = getImatge().getY();
            main.remove(getImatge());
            setImatge(imatges.get(0));
            getImatge().setLocation(x, y);
            indexImatge = 0;
        }
        main.add(getImatge());
    }

    /**
     * Mètode per obtenir les imatges de l'helicopter.
     *
     * @return --> ArrayList amb les imatges.
     */
    final ArrayList<GImage> getImatges() {
        return imatges;
    }

    /**
     * Mètode per assignar unes imatges a l'helicopter.
     *
     * @param imatgesN
     *            --> ArrayList amb les imatges.
     */
    final void setImatges(final ArrayList<GImage> imatgesN) {
        this.imatges = imatgesN;
    }

    /**
     * Generar valor aleatori per determinar des de on ha de sortir
     * l'helicopter.
     *
     * @return --> 0 o 1.
     */
    final int generarRandom() {
        Random rnd = new Random();
        return (int) (rnd.nextDouble() * 2);

    }

    /**
     * Mètode per obtenir l'ubicació d'un helicopter.
     *
     * @return --> Retorna un enter corresponent a l'ubicació de l'helicopter.
     */
    final int getUbicacio() {
        return ubicacio;
    }

    /**
     * Mètode per assignar l'ubicació d'un helicopter.
     *
     * @param ubicacioN
     *            --> Enter corresponent a l'ubicació. 0 Esquerra / 1 Dreta.
     */
    final void setUbicacio(final int ubicacioN) {
        this.ubicacio = ubicacioN;
    }

    @Override
    public final void startPlay(final Main finestra,
            final ObjecteEnMoviment helicopter) {
        // TODO Auto-generated method stub

    }

}
