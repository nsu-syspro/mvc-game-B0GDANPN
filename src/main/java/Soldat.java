import acm.graphics.GImage;

/**
 * Mètode per crear objectes de tipus "Soldat".
 *
 * @author Jose Garvin Victoria.
 *
 */
public class Soldat extends ObjecteEnMoviment {

    /**
     * Velocitat del soldat quan baixa amb paracaigudes.
     */
    private static final double VELOCITATSOLDATAMBPARACA = 0.5;

    /**
     * Boolean per determinar si el soldat ha saltat de l'helicopter.
     */
    private boolean haSaltat;

    /**
     * Coordenada X de salt.
     */
    private double xSalt = 0;

    /**
     * Boolean per determinar si el soldat ha tocat el terra.
     */
    private boolean tocaTerra;

    /**
     * Variable on s'emmagatzema el paracaigudes del soldat.
     */
    private Paracaigudes paraca;

    /**
     * Boolean per determinar si el soldat ha obert el paracaigudes.
     */
    private boolean paracaObert;



    /**
     * Constructor per crear objectes de tipus "Soldat".
     * @param x --> Coordenada X de salt del soldat.
     */
    public Soldat(final int x) {
        // TODO Auto-generated constructor stub
        this.setImatge(new GImage("resources/soldat/soldierMini.png", x, 0));
        haSaltat = false;
        xSalt = x;
        paracaObert = false;
        this.setHaSortit(haSaltat);
    }

    /**
     * Mètode per fer que un soldat salti de l'helicopter.
     *
     * @param finestra
     *            --> Finestra del joc.
     * @param helicopter
     *            --> Helicopter del qual ha saltat el soldat.
     */
    final void saltarHelicopter(final Main finestra,
            final ObjecteEnMoviment helicopter) {
        this.getImatge().setLocation(helicopter.getImatge().getX() + 55,
                helicopter.getImatge().getY() + 55);
        finestra.add(this.getImatge());

        this.getImatge().move(0, 1);

    }

    @Override
    public final void startPlay(final Main finestra) {
        // TODO Auto-generated method stub

    }

    @Override
    public final void startPlay(final Main finestra,
            final ObjecteEnMoviment helicopter) {
        // TODO Auto-generated method stub

        // Si el soldat no ha saltat.
        if (!this.isHaSaltat()) {

            // Quan s'arriba a la pos de salt el fem saltar.
            if (helicopter.getImatge().getX() == this.getxSalt()) {
                // System.out.println("Salta!");
                this.saltarHelicopter(finestra, helicopter);
                this.setHaSaltat(true);
            }

        } // Si el soldat ha saltat i no ha tocat terra
        if (this.haSaltat && !this.tocaTerra) {
            this.caure();

            if (this.getImatge().getY() > finestra.getHeight() / 2) {

                if (!paracaObert) {
                    // System.out.println("Obrir paracaigudes!");
                    obrirParaca(finestra);
                    this.paracaObert = true;
                }

            }

            if (this.getImatge().getY() == (finestra.getHeight() - this
                    .getImatge().getHeight())) {
                this.setTocaTerra(true);
                finestra.remove(paraca.getImatge());
                this.paraca = null;

            }

        }

    }

    /**
     * Mètode per obrir el paracaigudes del soldat.
     *
     * @param finestra
     *            --> Finestra del joc.
     */
    final void obrirParaca(final Main finestra) {
        Paracaigudes paracaigudes = new Paracaigudes();
        this.paraca = paracaigudes;
        paraca.getImatge().setLocation(this.getImatge().getX() + 5,
                this.getImatge().getY() - 30);
        finestra.add(paraca.getImatge());
    }

    /**
     * Mètode per fer que el soldat es mogui al saltar de l'helicopter.
     */
    final void caure() {

        if (this.paraca == null) {
            this.getImatge().move(0, 2);
        }
        if (this.paraca != null) {

            this.getImatge().move(0, VELOCITATSOLDATAMBPARACA);
            this.paraca.getImatge().move(0, VELOCITATSOLDATAMBPARACA);
        }

    }

    /**
     * Mètode per determinar si un soldat ha tocat el terra.
     *
     * @return --> True/Si el soldat a tocat el terra. False/SI encara no ha
     *         arribat.
     */
    final boolean isTocaTerra() {
        return tocaTerra;
    }

    /**
     * Mètode per determinar assignar valor a la propietat tocaTerra.
     *
     * @param tocaTerraN
     *            --> Boolean a assignar.
     */
    final void setTocaTerra(final boolean tocaTerraN) {
        this.tocaTerra = tocaTerraN;
    }

    /**
     * Mètode per determinar si un soldat ha saltat.
     *
     * @return --> True/Si el soldat a saltat. False/SI encara no ha saltat.
     */
    final boolean isHaSaltat() {
        return haSaltat;
    }

    /**
     * Mètode per assignar valor a la propietat haSaltat.
     *
     * @param haSaltatN
     *            --> Boolean a assignar.
     */
    final void setHaSaltat(final boolean haSaltatN) {
        this.haSaltat = haSaltatN;
    }

    /**
     * Mètode per obtenir la X de salt d'un soldat.
     *
     * @return --> Retorna la coordenada X de salt del soldat.
     */
    final double getxSalt() {
        return xSalt;
    }

    /**
     * Mètode per assignar la X de salt del soldat.
     *
     * @param xSaltN
     *            --> Coordenada X de salt.
     */
    final void setxSalt(final double xSaltN) {
        this.xSalt = xSaltN;
    }

}
