import acm.graphics.GImage;

/**
 * Classe per crear objectes de tipus "Bala".
 *
 * @author Jose Garvin Victoria
 *
 */
public class Bala extends ObjecteEnMoviment {

    /**
     * Velocitat de la bala.
     */
    private static final int VELOCITAT = 7;

    /**
     * Coordenada X d'inici de la bala.
     */
    private double x;

    /**
     * Coordenada Y d'inici de la bala.
     */
    private double y;

    /**
     * Constructor d'objectes de tipus "Bala".
     *
     * @param inicix
     *            --> Coordenada X d'inici de la bala.
     * @param iniciy
     *            --> Coordenada Y d'inici de la bala.
     */
    Bala(final double inicix, final double iniciy) {
        this.setImatge(new GImage("resources/cano/bala.png"));
        x = inicix;
        y = iniciy;
        setHaSortit(false);
    }

    /**
     * Mètode per moure una bala.
     */
    final void moureBala() {
        // this.getImatge().move(0, -5);
        this.getImatge().move(this.x * -VELOCITAT, this.y * (VELOCITAT * -1));
    }

    @Override
    public final void startPlay(final Main finestra) {
        // TODO Auto-generated method stub

        if (this.estaDinsFinestra(finestra, getImatge())) {
            if (!balaMata(finestra)) {
                this.moureBala();

            }

        } else {
            finestra.getObjectesEnMoviment().remove(this);
            this.setHaSortit(true);
        }

    }

    /**
     * Mètode per comprovar si una bala toca a un objectiu.
     *
     * @param finestra
     *            --> Finestra del joc
     * @return --> True--> Si la bala toca alguna cosa. False --> Si la bala no
     *         toca res.
     */
    final boolean balaMata(final Main finestra) {

        // Codi de prova!
        for (int i = finestra.getObjectesEnMoviment().size() - 1; i > 0; i--) {

            boolean toca = this
                    .getImatge()
                    .getBounds()
                    .intersects(
                            finestra.getObjectesEnMoviment().get(i).getImatge()
                                    .getBounds())
                    && !(finestra.getObjectesEnMoviment().get(i)
                            instanceof Bala);
            if (toca) {
                System.out.println("La bala toca alguna cosa!");
                finestra.getObjectesEnMoviment()
                        .get(i)
                        .getImatge()
                        .getParent()
                        .remove(finestra.getObjectesEnMoviment().get(i)
                                .getImatge());
                this.getImatge().getParent().remove(this.getImatge());
                return true;

            }
            break;

        }
        return false;
    }

    @Override
    public void startPlay(final Main finestra,
            final ObjecteEnMoviment helicopter) {
        // TODO Auto-generated method stub

    }

}
