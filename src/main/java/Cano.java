import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import acm.graphics.GImage;

/**
 * Classe per crear objectes de tipus "Cano".
 *
 * @author Jose Garvin Victoria.
 *
 */
public class Cano {

    /**
     * Imatge corresponent a la base del cano.
     */
    private GImage baseCano;

    /**
     * Imatge corresponent al tub del cano.
     */
    private GImage tubCano;

    /**
     * Posicio X de la base del cano.
     */
    private double posXBase;

    /**
     * Posicio Y de la base del cano.
     */
    private double posYBase;

    /**
     * Graus corresponents a l'angle del cano.
     */
    private double orientacio;

    /**
     * Constructor per a objectes de tipus "Cano".
     *
     * @param base
     *            --> Imatge de la base del cano.
     * @param tub
     *            --> Imatge del tub del cado.
     * @param finestra
     *            --> Finestra del joc.
     */
    Cano(final GImage base, final GImage tub, final Main finestra) {
        this.baseCano = base;
        this.tubCano = tub;
        this.posXBase = Main.getTamanyX();
        this.posYBase = Main.getTamanyY() - base.getHeight();
        this.orientacio = 0;
    }

    /**
     * Mètode per rotar el tub del cano.
     *
     * @param graus
     *            --> Graus corresponent a la rotació del tub del cano.
     */
    final void rotarTubCano(final int graus) {

        this.tubCano.setImage((rotateMyImage(toBufferedImage(this.getTubCano()
                .getImage()), graus)));
    }

    /**
     * Mètode per crear una bala.
     *
     * @param iniciX
     *            --> Coordenada X d'inici del trajecte de la bala.
     * @param iniciY
     *            --> Coordenada Y d'inici del trajecte de la bala.
     * @return --> Retorna un objecte "Bala" amb les coordenades d'inici
     *         inicialitzades.
     */
    final Bala dispara(final double iniciX, final double iniciY) {

        Bala bala = new Bala(iniciX, iniciY);
        // System.out.println("Bala Creada!");
        return bala;
    }

    /**
     * Mètode per rotar una imatge especificant-hi els graus.
     *
     * @param img
     *            --> Imatge inicial.
     * @param angle
     *            --> Graus de rotació.
     * @return --> Retorna la imatge rotada.
     */
    final Image rotateMyImage(final BufferedImage img, final double angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.rotate(Math.toRadians(angle), w / 2, h / 2);

        g.drawImage(img, null, 0, 0);

        return (Image) dimg;
    }

    /**
     * Converts a given Image into a BufferedImage.
     *
     * @param img
     *            --> The Image to be converted
     * @return --> The converted BufferedImage
     */
    final BufferedImage toBufferedImage(final Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    /**
     * Mètode per obtenir l'orientació del cano.
     *
     * @return --> Retorna l'orientació en graus.
     */
    final double getOrientacio() {
        return orientacio;
    }

    /**
     * Mètode per assignar l'orientació del cano.
     *
     * @param orientacioC
     *            --> Graus corresponents a l'orientació del cano.
     */
    final void setOrientacio(final double orientacioC) {
        this.orientacio = orientacioC;
    }

    /**
     * Mètode per obtenir la imatge del tub del cano.
     * @return --> Retorna la imatge del tub del cano.
     */
    final GImage getTubCano() {
        return tubCano;
    }

    /**
     * Mètode per assignar una imatge al tub del cano.
     * @param tubCanoN --> Imatge corresponent al tub del cano.
     */
    final void setTubCano(final GImage tubCanoN) {
        this.tubCano = tubCanoN;
    }

    /**
     * Mètode per obtenir la imatge corresponent a la base del cano.
     * @return --> Retorna la imatge de la base del cano.
     */
    final GImage getBaseCano() {
        return baseCano;
    }

    /**
     * Mètode per assignar una imatge a la base del cano.
     * @param baseCanoN --> Imatge corresponent a la base del cano.
     */
    final void setBaseCano(final GImage baseCanoN) {
        this.baseCano = baseCanoN;
    }

}
