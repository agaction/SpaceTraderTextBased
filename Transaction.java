import java.io.Serializable;

public class Transaction implements Serializable {

    public void buy(Player p, TradeGood t, int quantity) {
        if (!p.getShip().canBuy(quantity)) {
            System.out.println("You do not have enough free cargo holds "
                                + "to buy these many goods! Please try again.");
            return;
        }
        if (!(p.getCredits() > t.getCurrentPrice() * quantity)) {
            System.out.println("You do not have enough credits to buy "
                                + "all of these goods! Please try again.");
            return;
        }

        p.setCredits(p.getCredits() - (t.getCurrentPrice() * quantity));
        p.getShip().addToList(t, quantity);
        System.out.println("You bought " + quantity + " "
            + t.name() + "! You now have " + p.getCredits()
            + " credits left. if you would like to keep buying, "
            + "please enter another purchase. If you would like "
            + "to quit, please press 'q'");
    }

    public void sell(Player p, TradeGood t, int quantity) {
        if ((p.getSystem().getTechLevel().getRank() < t.getMTLU())) {
            System.out.println("This good's tech level is too high to be sold "
                                + "on this system! Please try again.");
            return;
        }
        if ((quantity > p.getShip().getQuantity())) {
            System.out.println("You do not have these many goods to sell!"
                                                    + "Please try again.");
            return;
        }

        p.getShip().remove(t, quantity);
        p.setCredits(p.getCredits() + (t.getCurrentPrice() * quantity));
        return;
    }
}
