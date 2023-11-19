import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe principal del programa "Paratrooper".
 *
 * @author Jose Garvin Victoria
 *
 */
public class Main extends GraphicsProgram {

    /**
     * Graus de rotació al polsar la tecla.
     */
    public int main(){
        run();
    }
    private static final double GRAUS = 0.25;

    /**
     * Valor corresponent a la rotació màxima.
     */
    private static final double TOPE_GRAUS = 1.75;

    /**
     * Graus de rotació del cano.
     */
    private static final int GRAUSROTACIO = 5;

    /**
     * Coordenada Y corresponent a la segona fila.
     */
    private static final int Y_SEGONA_FILA = 95;

    /**
     * Número màxim d'helicopters aleatoris.
     */
    private static final int MAX_HELI_ALEATORIS = 4;

    /**
     * Valor corresponent a la probabilitat de que es creein nous helicopters.
     */
    private static final int VALOR_PROBABILTAT_NOUS_HELI = 500;

    /**
     * Valor corresponent a la pausa.
     */
    private static final int VALORPAUSE = 25;

    /**
     * ArrayList on s'emmagatzemen tots els objectes en moviment del joc.
     */
    private ArrayList<ObjecteEnMoviment> objectesEnMoviment = new ArrayList<ObjecteEnMoviment>();

    /**
     * Tamany Y de la finestra.
     */
    private static final int TAMANY_Y = 540;

    /**
     * Tamany X de la finestra.
     */
    private static final int TAMANY_X = 900;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creació de l'objecte "Cano".
     */
    private Cano cano = new Cano(new GImage("resources/cano/canonbase.png"),
            new GImage("resources/cano/canontub.png"), this);

    /**
     * Mètode principal del programa.
     */
    public final void run() {

        /**
         * Assignem un tamany a la finestra.
         */
        this.setSize(TAMANY_X, TAMANY_Y);

        mostrarBenvinguda();

        controlInici();

        addKeyListeners(this);

        iniciarJoc();

    }

    /**
     * Mètode per mostrar la benvinguda a l'usuari.
     */
    final void mostrarBenvinguda() {
        add(new GImage("resources/startGame.png"));
    }

    /**
     * Mètode per iniciar el joc.
     */
    final void iniciarJoc() {
        /**
         * Assignem la imatge de fons.
         */
        add(new GImage("resources/gamebackground.jpg"));

        /**
         * Afegim la base del cano i el col.loquem.
         */
        add(cano.getBaseCano());
        cano.getBaseCano().setLocation(
                Main.getTamanyX() / 2 - ((cano.getBaseCano().getWidth()) / 2),
                Main.getTamanyY() - cano.getBaseCano().getHeight());

        /**
         * Afegim el tub del cano i el col.loquem.
         */
        add(cano.getTubCano());
        cano.getTubCano().setLocation(
                Main.getTamanyX() / 2 - ((cano.getBaseCano().getWidth()) / 2),
                Main.getTamanyY() - cano.getBaseCano().getHeight());

        boolean jocFinalitzat = false;

        Helicopter helicopterPare = new Helicopter();

        while (!jocFinalitzat) {
            generarHelicopters();

            for (int i = 0; i < objectesEnMoviment.size(); i++) {

                // Desem els helicopters en una variable per poder pasarlos al
                // mètode startPlay dels soldats.

                if (!objectesEnMoviment.get(i).isHaSortit()) {

                    // Si trobem un soldat:
                    if (objectesEnMoviment.get(i) instanceof Soldat) {

                        objectesEnMoviment.get(i).startPlay(this,
                                helicopterPare);

                        // Si trobem un helicopter:
                    }
                    if (objectesEnMoviment.get(i) instanceof Helicopter) {
                        helicopterPare = (Helicopter) objectesEnMoviment.get(i);
                        objectesEnMoviment.get(i).startPlay(this);

                        // Si trobem una bala:
                    } else {
                        objectesEnMoviment.get(i).startPlay(this);
                    }

                }

            }
            this.pause(VALORPAUSE);

        }
    }

    /**
     * Mètode per generar helicopters aleatoris.
     */
    final void generarHelicopters() {
        int x = generarRandom(VALOR_PROBABILTAT_NOUS_HELI);
        int numHelicopters = generarRandom(MAX_HELI_ALEATORIS);
        if (x == 1) {
            for (int i = 0; i < numHelicopters; i++) {
                crearHelicopterAleatori();
            }
        }
    }

    /**
     * Mètode per crear un helicopter aleatori.
     */
    final void crearHelicopterAleatori() {
        /**
         * Creem un helicopter
         */

        Helicopter helicopter = new Helicopter(assignarImatgeHeli());
        if (helicopter.getUbicacio() == 0) {
            helicopter.getImatge().setLocation(
                    0 - helicopter.getImatge().getWidth(),
                    generarFilaAleatoria());
            helicopter.flipHorizontal(helicopter.getImatges().get(0));
            helicopter.flipHorizontal(helicopter.getImatges().get(1));
            this.add(helicopter.getImatge());

        } else {
            helicopter.getImatge().setLocation(this.getWidth(),
                    generarFilaAleatoria());
            this.add(helicopter.getImatge());
        }

        objectesEnMoviment.add(helicopter);
        generarSoldats(generarRandom(2));

    }

    /**
     * Mètode per generar un numero aleatori de soldats.
     *
     * @param numSoldats
     *            --> Número aleatori de soldats.
     */
    final void generarSoldats(final int numSoldats) {
        // System.out.println("Nº de soldats a bord: " + numSoldats);

        for (int i = 0; i < numSoldats; i++) {
            int xSalt = generarRandom(TAMANY_X);
            Soldat soldat = new Soldat(xSalt);
            objectesEnMoviment.add(soldat);
        }
    }

    /**
     * Mètode per generar una fila aleatoria per a l'helicopter.
     *
     * @return --> Retorna la posicio Y on anira l'helicopter.
     */
    final int generarFilaAleatoria() {
        int ran = generarRandom01();
        if (ran == 0) {
            return 0;
        }
        return Y_SEGONA_FILA;

    }

    /**
     * Mètode per generar un valor aleatori entre 0 i el valor passat per
     * paràmetre.
     *
     * @param valor
     *            --> Valor referencia per obtenir un random
     * @return --> Retorna un valor aleatori.
     */
    final int generarRandom(final int valor) {
        Random rnd = new Random();
        return (int) (rnd.nextDouble() * valor) + 1;
    }

    /**
     * Mètode per generar un valor aleatori. 0 o 1.
     *
     * @return --> Retorna un valor aleatori.
     */
    final int generarRandom01() {
        Random rnd = new Random();
        return (int) (rnd.nextDouble() * 2);

    }

    /**
     * Mètode per mostrar l'inici del programa a l'usuari.
     */
    final void controlInici() {
        GLabel glabel = new GLabel("");
        glabel.setLocation(TAMANY_X / 2 - (glabel.getWidth() / 2), TAMANY_Y / 2);

        add(glabel);
        waitForClick();
        remove(glabel);

    }

    /**
     * Mètode per assignar imatges als helicopters.
     *
     * @return --> Retorna un arraylist amb les imatges.
     */
    final ArrayList<GImage> assignarImatgeHeli() {
        ArrayList<GImage> imatgesHeli = new ArrayList<GImage>();
        imatgesHeli.add(new GImage("resources/minihelicopter1.png"));
        imatgesHeli.add(new GImage("resources/minihelicopter1-1.png"));
        return imatgesHeli;

    }

    /**
     * Mètode per controlar els events de teclat.
     *
     * @param e
     *            --> Event realitzat.
     *
     */
    public final void keyPressed(final KeyEvent e) {

        if (e.VK_LEFT == e.getKeyCode()) {
            cano.rotarTubCano(GRAUSROTACIO * -1);

            if (cano.getOrientacio() < TOPE_GRAUS) {
                double orientacioActual = cano.getOrientacio();
                cano.setOrientacio(orientacioActual += GRAUS);
            }

            System.out.println(cano.getOrientacio());

        }
        if (e.VK_RIGHT == e.getKeyCode()) {
            cano.rotarTubCano(GRAUSROTACIO);

            if (cano.getOrientacio() > TOPE_GRAUS * -1) {
                double orientacioActual = cano.getOrientacio();
                cano.setOrientacio(orientacioActual -= GRAUS);
            }

            System.out.println(cano.getOrientacio());

        }
        if (e.VK_SPACE == e.getKeyCode()) {

            // Calcul angles bala
            double balaX = Math.sin(cano.getOrientacio());
            double balaY = Math.cos(cano.getOrientacio());

            Bala novaBala = cano.dispara(balaX, balaY);
            objectesEnMoviment.add(novaBala);
            novaBala.getImatge().setLocation(cano.getTubCano().getX() + 23,
                    cano.getTubCano().getY() - 8);
            this.add(novaBala.getImatge());
            // System.out.println("Bala afegida!");
            System.out.println(objectesEnMoviment.toString());
        }
    }

    /**
     * Mètode per obtenir el tamany Y de la finestra.
     *
     * @return --> Retorna un int corresponent al tamany Y de la finestra.
     */
    public static int getTamanyY() {
        return TAMANY_Y;
    }

    /**
     * Mètode per obtenir el tamany X de la finestra.
     *
     * @return --> Retorna un int corresponent al tamany X de la finestra.
     */
    public static int getTamanyX() {
        return TAMANY_X;
    }

    /**
     * Mètode per obtenir l'arraylist on s'emmagatzemen els objectes
     * en moviment.
     * @return --> ArrayList amb els objectes en moviment.
     */
    final ArrayList<ObjecteEnMoviment> getObjectesEnMoviment() {
        return objectesEnMoviment;
    }



}