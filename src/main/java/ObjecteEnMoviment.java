import acm.graphics.GImage;
import acm.graphics.GRectangle;

/**
 * Classe abstracta per a objectes en moviment.
 *
 * @author Jose Garvin Victoria.
 *
 */
public abstract class ObjecteEnMoviment {

    /**
     * Imatge de l'objecte en moviment.
     */
    private GImage imatge;

    /**
     * Boolean que determina si un objecte ha sortir de la finestra.
     */
    private boolean haSortit;

    /**
     * Mètode abstracte per inicialitzar un objecte en moviment.
     *
     * @param finestra
     *            --> Finestra del joc.
     */
    public abstract void startPlay(Main finestra);

    /**
     * Mètode abstracte per inicialitzar un objecte en moviment.
     *
     * @param finestra
     *            --> Finestra del joc.
     * @param helicopter
     *            --> Objecte "Helicopter".
     */
    public abstract void startPlay(Main finestra, ObjecteEnMoviment helicopter);

    /**
     * Mètode per comprovar si els helicopters estan dins de la finestra.
     *
     * @param finestra
     *            --> Finestra del joc.
     * @param imatgeOM
     *            --> Imatge de l'objecte a comprovar.
     * @return --> True/ Si l'objecte esta dins de la finestra. False/ Si no ho
     *         esta.
     */
    public final boolean estaDinsFinestra(final Main finestra,
            final GImage imatgeOM) {
        GRectangle grFinestra = new GRectangle(0, 0, finestra.getWidth(),
                finestra.getHeight());
        boolean estaDins = grFinestra.getBounds().intersects(
                imatgeOM.getBounds());
        if (!estaDins) {
            System.out.println("Objecte fora de la finestra! ELIMINAT!");
            imatgeOM.getParent().remove(imatgeOM);
            return estaDins;
        }
        return estaDins;
    }

    /**
     * Mètode per invertir l'imatge d'un soldat.
     * @param imatgeN --> Imatge a invertir.
     */
    final void flipHorizontal(final GImage imatgeN) {
        int[][] array = imatgeN.getPixelArray();
        int height = array.length;
        int width = array[0].length;

        for (int y = 0; y < height; y++) {
            for (int x1 = 0; x1 < width / 2; x1++) {
                int x2 = width - x1 - 1;
                int temp = array[y][x1];
                array[y][x1] = array[y][x2];
                array[y][x2] = temp;
            }
        }
        imatgeN.setImage(new GImage(array).getImage());
    }

    /**
     * Mètode per obtenir la imatge de un objecte en moviment.
     * @return --> Retorna la imatge de l'objecte.
     */
    final GImage getImatge() {
        return imatge;
    }

    /**
     * Mètode per assignar una imatge a un  objecte en moviment.
     * @param imatgeN --> Imatge a assignar.
     */
    final void setImatge(final GImage imatgeN) {
        this.imatge = imatgeN;
    }

    /**
     * Mètode per obtenir la variable haSortit.
     * @return --> Retorna el valor de la variable.
     */
    final boolean isHaSortit() {
        return haSortit;
    }

    /**
     * Mètode per assignar un valor a la variable haSortit.
     * @param haSortitN --> Valor a assignar.
     */
    final void setHaSortit(final boolean haSortitN) {
        this.haSortit = haSortitN;
    }

}
